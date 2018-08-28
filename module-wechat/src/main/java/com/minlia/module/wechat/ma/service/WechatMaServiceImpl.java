package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.alibaba.fastjson.JSON;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.body.BibleItemQueryRequestBody;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.wechat.ma.body.MiniappQrcodeRequestBody;
import com.minlia.module.wechat.ma.config.PhoneNumberRequestBody;
import com.minlia.module.wechat.ma.constant.WechatMaBibleConstants;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.mp.constant.WechatMpApiCode;
import com.minlia.module.wechat.utils.HttpClientUtil;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.body.AttachmentUploadRequestBody;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import me.chanjar.weixin.common.error.WxErrorException;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;

/**
 *
 * @author will
 * @date 6/26/17
 */
@Service
@Transactional
public class WechatMaServiceImpl implements WechatMaService {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private BibleItemService bibleItemService;
    @Autowired
    private AttachmentUploadService attachmentUploadService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    static String DEFAULT_QRCODE_PATH = "qrcode";

    @Override
    public WxMaService getWxMaService(String type) {
        BibleItem bibleItem = bibleItemService.queryOne(BibleItemQueryRequestBody.builder().parentCode(WechatMaBibleConstants.MINIAPP_CODE).code(type).build());
        ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND,type+"类型的小程序参数数据字典未配置");

        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        wxMaConfig.setAppid(bibleItem.getValue());
        wxMaConfig.setSecret(bibleItem.getAttribute1());
//        wxMaConfig.setExpiresTime(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        wxMaConfig.setExpiresTime(System.currentTimeMillis());

        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String code) {
        return getSessionInfo(wxMaService,code);
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String wxMaType, String code) {
        WxMaService wxMaService = StringUtils.isEmpty(wxMaType) ? this.wxMaService : getWxMaService(wxMaType);
        return getSessionInfo(wxMaService,code);
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(WxMaService wxMaService, String code) {
        WxMaJscode2SessionResult result = null;
        try {
            result = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiPreconditions.is(true, ApiCode.BASED_ON, "远程获取小程序session失败：" + e.getError());
        }
        return result;
    }

    @Override
    public WxMaPhoneNumberInfo getBoundPhoneNumber(PhoneNumberRequestBody body) {
        WxMaJscode2SessionResult session = this.getSessionInfo(body.getCode());
        WxMaPhoneNumberInfo numberInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(),body.getEncryptedData(), body.getIv());
        return numberInfo;
    }

    private WechatOpenAccount bindWechatOpenAccount(WechatOpenAccount wechatOpenAccount, String guid) {
        wechatOpenAccount.setGuid(guid);
        return wechatOpenAccountService.update(wechatOpenAccount);
    }

    @Override
    public OssFile createWxCodeLimit(MiniappQrcodeRequestBody body){
        BibleItem qrConfig = bibleItemService.queryOne(BibleItemQueryRequestBody.builder().parentCode(WechatMaBibleConstants.WECHAT_MA_QR_TYPE).code(body.getType()).build());
        ApiPreconditions.is(null == qrConfig,ApiCode.NOT_FOUND,"小程序二维码类型不存在");

        String accessToken = null;
        try {
            accessToken = wxMaService.getAccessToken();
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_GET_ACCESS_TOKEN,"获取Access Token异常："+e.getMessage());
        }
        ApiPreconditions.checkNotNull(accessToken,ApiCode.NOT_NULL,"accessToken不能为空");
        String reqUrl = String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", accessToken);

        String page = String.format(qrConfig.getValue(), body.getScene());
        ApiPreconditions.is(page.length()>32, 50005, "路径长度不能超过32个字符");

        JSONObject data = new JSONObject();
        data.element("scene", body.getScene());
        data.element("page",qrConfig.getValue());
        data.element("is_hyaline", null == body.getIsHyaline() ? true : body.getIsHyaline());
        if (null != body.getWidth()) {
            data.element("width", body.getWidth());
        }
        if (null != body.getAutoColor()) {
            data.element("auto_color", body.getAutoColor());
        }
        if (null != body.getLineColor()) {
            data.element("line_color", body.getLineColor());
        }
        String reqData = JSON.toJSONString(data);


//        InputStream in = null;
//        try {
//            in = HttpClientUtil.sendPostByJsonToInputStream(reqUrl, reqData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ApiPreconditions.is(true, WechatMpApiCode.ERROR_CREATE_WX_CODE,"生成小程序二维码异常："+e.getMessage());
//        }
//
//        if (!body.isNotUpload()) {
//            //上传附件
//            String path = null == body.getPath() ? String.format("%s/", DEFAULT_QRCODE_PATH) : String.format("%s/%s/", DEFAULT_QRCODE_PATH, body.getPath());
//            String key = String.format("%s%s.png", path, NumberGenerator.uuid32());
//            AttachmentUploadRequestBody requestBody = AttachmentUploadRequestBody.builder()
//                    .relationId(body.getNumber())
//                    .belongsTo(body.getType())
//                    .inputStream(in)
//                    .key(key)
//                    .build();
//            return (OssFile) attachmentUploadService.upload(requestBody).getPayload();
//        }


        File file = null;
        try {
            file = HttpClientUtil.sendPostByJsonToFile(reqUrl, reqData);
        } catch (Exception e) {
            e.printStackTrace();
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_CREATE_WX_CODE,"生成小程序二维码异常："+e.getMessage());
        }


        try {
            if (!body.isNotUpload()) {
                //上传附件
                String path = null == body.getPath() ? String.format("%s/", DEFAULT_QRCODE_PATH) : String.format("%s/%s/", DEFAULT_QRCODE_PATH, body.getPath());
                AttachmentUploadRequestBody requestBody = AttachmentUploadRequestBody.builder()
                        .relationId(body.getNumber())
                        .belongsTo(body.getType())
                        .file(file)
                        .key(path + file.getName())
                        .build();
                return (OssFile) attachmentUploadService.upload(requestBody).getPayload();
            }
        } catch (Exception e) {
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_GET_ACCESS_TOKEN,"OSS上传异常："+e.getMessage());
        } finally {
            //删除文件
            FileUtils.deleteQuietly(file);
        }
        return null;
    }
}
