package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.wechat.ma.body.MiniappQrcodeRequestBody;
import com.minlia.module.wechat.ma.body.WechatSession;
import com.minlia.module.wechat.ma.constant.WechatMaBibleConstants;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.mp.constant.WechatMpApiCode;
import com.minlia.module.wechat.utils.HttpClientUtil;
import com.minlia.modules.aliyun.oss.api.service.OssService;
import com.minlia.modules.aliyun.oss.bean.OssFile;
import com.minlia.modules.attachment.body.AttachmentCreateRequestBody;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.service.AttachmentService;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author will
 * @date 6/26/17
 */
@Service
@Transactional
public class WechatMiniappServiceImpl implements WechatMiniappService {

    @Autowired
    private OssService ossService;
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private BibleItemService bibleItemService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public WxMaService getWxMaService(String type) {
        BibleItem bibleItem = bibleItemService.queryByParentCodeAndCode(WechatMaBibleConstants.MINIAPP_CODE,type);
        ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND,type+"类型的小程序参数数据字典未配置");

        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        wxMaConfig.setAppid(bibleItem.getValue());
        wxMaConfig.setSecret(bibleItem.getAttribute1());
        wxMaConfig.setExpiresTime(System.currentTimeMillis());

        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

    @Override
    public WechatSession getSessionInfo(String code) {
        return getSessionInfo(wxMaService,code);
    }

    @Override
    public WechatSession getSessionInfo(String wxMaType, String code) {
        WxMaService wxMaService = StringUtils.isEmpty(wxMaType) ? this.wxMaService : getWxMaService(wxMaType);
        return getSessionInfo(wxMaService,code);
    }

    @Override
    public WechatSession getSessionInfo(WxMaService wxMaService,String code) {
        WxMaConfig config = wxMaService.getWxMaConfig();
        Map<String, String> params = new HashMap();
        params.put("appid", config.getAppid());
        params.put("secret", config.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String result = null;
        try {
            result = wxMaService.get("https://api.weixin.qq.com/sns/jscode2session", Joiner.on("&").withKeyValueSeparator("=").join(params));
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiPreconditions.is(true, ApiCode.BASED_ON, "远程获取小程序session失败：" + e.getError());
        }
        return WxMaGsonBuilder.create().fromJson(result, WechatSession.class);
    }

    private WechatOpenAccount bindWechatOpenAccount(WechatOpenAccount wechatOpenAccount, String guid) {
        wechatOpenAccount.setGuid(guid);
        return wechatOpenAccountService.update(wechatOpenAccount);
    }

    @Override
    public OssFile createWxCodeLimit(MiniappQrcodeRequestBody body){
        String accessToken = null;
        try {
            accessToken = wxMaService.getAccessToken();
        } catch (WxErrorException e) {
            e.printStackTrace();
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_GET_ACCESS_TOKEN,"获取Access Token异常："+e.getMessage());
        }
        ApiPreconditions.checkNotNull(accessToken,ApiCode.NOT_NULL,"accessToken不能为空");
        String reqUrl = String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", accessToken);

        String page = String.format(body.getPage(), body.getScene());
        ApiPreconditions.is(page.length()>32, 50005, "路径长度不能超过32个字符");

        JSONObject data = new JSONObject();
        data.element("scene", body.getScene());
        data.element("page",body.getPage());
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

        File file = null;
        try {
            file = HttpClientUtil.sendPostByJsonToFile(reqUrl, reqData);
        } catch (Exception e) {
            e.printStackTrace();
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_CREATE_WX_CODE,"创建二维码异常："+e.getMessage());
        }
        OssFile ossFile = null;
        try {
            ossFile = ossService.upload(file, "qrcode"+file.getName());

            attachmentService.create(Attachment.builder()
                    .relationId("")
                    .belongsTo("QRCODE")
                    .name(ossFile.getOriginalName())
                    .size(ossFile.getSize())
                    .type(ossFile.getContentType())
                    .url(ossFile.getUrl())
                    .accessKey(ossFile.geteTag())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            ApiPreconditions.is(true, WechatMpApiCode.ERROR_GET_ACCESS_TOKEN,"OSS上传异常："+e.getMessage());
        } finally {
            FileUtils.deleteQuietly(file);
        }
        return ossFile;
    }

}
