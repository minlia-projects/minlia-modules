package com.minlia.module.wechat.ma.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.ro.BibleItemQRO;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.ma.bean.ro.WechatMaEncryptedDataRO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaQrcodeRO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaUserDetailRO;
import com.minlia.module.wechat.ma.constant.WechatMaBibleConstants;
import com.minlia.module.wechat.ma.constant.WechatMaCode;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.ro.AttachmentUploadRO;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 *
 * @author will
 * @date 6/26/17
 */
@Slf4j
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
    private WechatUserService wechatUserService;

    static String DEFAULT_QRCODE_PATH = "qrcode";

    @Override
    public WxMaService getWxMaService(String type) {
        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        if (null != type) {
            BibleItem bibleItem = bibleItemService.queryOne(BibleItemQRO.builder().parentCode(WechatMaBibleConstants.MINIAPP_CODE).code(type).build());
            ApiAssert.notNull(bibleItem, WechatMaCode.Message.PARAMETER_NOT_CONFIG, type);
            wxMaConfig.setAppid(bibleItem.getValue());
            wxMaConfig.setSecret(bibleItem.getAttribute1());
        } else {
            wxMaConfig.setAppid(bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_APPID));
            wxMaConfig.setSecret(bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_SECRET));
        }
//        wxMaConfig.setExpiresTime(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        wxMaConfig.setExpiresTime(System.currentTimeMillis());

        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String code) {
        return getSessionInfo(wxMaService, code);
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String wxMaType, String code) {
        WxMaService wxMaService = StringUtils.isEmpty(wxMaType) ? this.wxMaService : getWxMaService(wxMaType);
        return getSessionInfo(wxMaService, code);
    }

    @Override
    public WxMaJscode2SessionResult getSessionInfo(WxMaService wxMaService, String code) {
        WxMaJscode2SessionResult result = null;
        try {
            result = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiAssert.state(false, "远程获取小程序session失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public WxMaPhoneNumberInfo getBoundPhoneNumber(WechatMaEncryptedDataRO body) {
        WxMaJscode2SessionResult session = this.getSessionInfo(body.getCode());
        WxMaPhoneNumberInfo numberInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(),body.getEncryptedData(), body.getIv());
        return numberInfo;
    }

    private WechatUser bindWechatOpenAccount(WechatUser wechatUser, String guid) {
        wechatUser.setGuid(guid);
        return wechatUserService.update(wechatUser);
    }

    @Override
    public WxMaUserInfo decrypt(WechatMaUserDetailRO ro) {
        WxMaService wxMaService = this.getWxMaService(ro.getType());
        WxMaJscode2SessionResult sessionResult = this.getSessionInfo(wxMaService,ro.getCode());
        return this.decrypt(wxMaService, sessionResult.getSessionKey(), ro.getEncryptedData(), ro.getIv());
    }

    @Override
    public WxMaUserInfo decrypt(WxMaService wxMaService, String sessionKey, String encryptedData, String iv) {
        try {
            return wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        } catch (Exception e) {
            log.error("小程序用户信息解密失败：{}", e);
            log.error("小程序用户信息解密sessionKey：{}", sessionKey);
            log.error("小程序用户信息解密iv：{}", iv);
            log.error("小程序用户信息解密data：{}", encryptedData);
            ApiAssert.state(false, "小程序用户信息解密失败");
        }
        return null;
    }



//    public OssFile createWxCodeLimit(WechatMaQrcodeRO body){
//        BibleItem qrConfig = bibleItemService.queryOne(BibleItemQRO.builder().parentCode(WechatMaBibleConstants.WECHAT_MA_QR_TYPE).code(body.getType()).build());
//        ApiAssert.notNull(qrConfig, WechatMaCode.Message.PARAMETER_NOT_CONFIG, body.getType());
//
//        String accessToken = null;
//        try {
//            accessToken = wxMaService.getAccessToken();
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//            ApiAssert.state(false, "获取Access Token异常："+e.getMessage());
//        }
//        ApiAssert.notNull(accessToken,"accessToken不能为空");
//        String reqUrl = String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", accessToken);
//
//        String page = String.format(qrConfig.getValue(), body.getScene());
//        ApiAssert.state(page.length()<=32,  "路径长度不能超过32个字符");
//
//        JSONObject data = new JSONObject();
//        data.element("scene", body.getScene());
//        data.element("page",qrConfig.getValue());
//        data.element("is_hyaline", null == body.getIsHyaline() ? true : body.getIsHyaline());
//        if (null != body.getWidth()) {
//            data.element("width", body.getWidth());
//        }
//        if (null != body.getAutoColor()) {
//            data.element("auto_color", body.getAutoColor());
//        }
//        if (null != body.getLineColor()) {
//            data.element("line_color", body.getLineColor());
//        }
//        String reqData = JSON.toJSONString(data);
//
//
//        File file = null;
//        try {
//            file = HttpClientUtil.sendPostByJsonToFile(reqUrl, reqData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ApiAssert.state(false,"生成小程序二维码异常："+e.getMessage());
//        }
//
//        try {
//            //上传附件
//            String path = null == body.getPath() ? String.format("%s/", DEFAULT_QRCODE_PATH) : String.format("%s/%s/", DEFAULT_QRCODE_PATH, body.getPath());
//            AttachmentUploadRO to = AttachmentUploadRO.builder()
//                    .relationId(body.getNumber())
//                    .belongsTo(body.getType())
//                    .file(file)
//                    .key(path + file.getName())
//                    .build();
//            return (OssFile) attachmentUploadService.upload(to).getPayload();
//        } catch (Exception e) {
//            ApiAssert.state(false,"OSS上传异常：" + e.getMessage());
//        } finally {
//            //删除文件
//            FileUtils.deleteQuietly(file);
//        }
//        return null;
//    }

    @Override
    public OssFile createWxCodeLimit(WechatMaQrcodeRO ro){
        File file = null;
        try {
            if (null == ro.getWidth()) {
                ro.setWidth(430);
            }
            file = wxMaService.getQrcodeService().createWxaCodeUnlimit(ro.getScene(), ro.getPath(), ro.getWidth(), ro.isAutoColor(), ro.getLineColor(), ro.isHyaline());
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiAssert.state(false,"生成小程序二维码异常："+e.getMessage());
        }

        try {
            //上传附件
            String path = null == ro.getPath() ? String.format("%s/", DEFAULT_QRCODE_PATH) : String.format("%s/%s/", DEFAULT_QRCODE_PATH, ro.getPath());
            AttachmentUploadRO to = AttachmentUploadRO.builder()
                    .relationId(ro.getBusinessId())
                    .belongsTo(ro.getBusinessType())
                    .file(file)
                    .key(path + file.getName())
                    .build();
            return (OssFile) attachmentUploadService.upload(to).getPayload();
        } catch (Exception e) {
            ApiAssert.state(false,"OSS上传异常：" + e.getMessage());
        } finally {
            //删除文件
            FileUtils.deleteQuietly(file);
        }
        return null;
    }

    @Override
    public OssFile createWxaCodeUnlimit(String scene, String path, String businessType, String businessId){
        File file = null;
        try {
            file = wxMaService.getQrcodeService().createWxaCodeUnlimit(scene, path);
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiAssert.state(false,"生成小程序二维码异常："+e.getMessage());
        }
        try {
            AttachmentUploadRO to = AttachmentUploadRO.builder()
                    .relationId(businessId)
                    .belongsTo(businessType)
                    .file(file)
                    .key(DEFAULT_QRCODE_PATH + "/" + file.getName())
                    .build();
            return (OssFile) attachmentUploadService.upload(to).getPayload();
        } catch (Exception e) {
            ApiAssert.state(false,"OSS上传异常：" + e.getMessage());
        } finally {
            FileUtils.deleteQuietly(file);
        }
        return null;
    }

}
