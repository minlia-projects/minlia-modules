package com.minlia.module.tencent.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import com.google.common.base.Joiner;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.bible.entity.BibleItem;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.tencent.miniapp.body.WechatSession;
import com.minlia.module.tencent.miniapp.constant.WechatMaBibleConstants;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 6/26/17.
 */
@Service
@Transactional
public class WechatMiniappServiceImpl implements WechatMiniappService {

//    @Autowired
//    private OssService ossService;
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private BibleItemService bibleItemService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public WxMaService getWxMaService(String type) {
        BibleItem bibleItem = bibleItemService.queryByParentCodeAndCode(WechatMaBibleConstants.MINIAPP_CODE,type);
        ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND,type+"类型的小程序参数数据字典未配置");

        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        wxMaConfig.setAppid(bibleItem.getLabel());
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

    private WechatOpenAccount bindWechatOpenAccount(WechatOpenAccount wechatOpenAccount,Long userId) {
        wechatOpenAccount.setUserId(userId);
        return wechatOpenAccountService.update(wechatOpenAccount);
    }

//    @Override
//    public OssFile createWxCodeLimit(MiniappQrcodeRequestBody body) throws Exception{
//        String accessToken = wxMaService.getAccessToken();
//        ApiPreconditions.checkNotNull(accessToken,ApiCode.NOT_NULL,"accessToken不能为空",false);
//        String reqUrl = String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", accessToken);
//
//        String page = String.format(body.getPage(), body.getScene());
//        ApiPreconditions.is(page.length()>32, 50005, "路径长度不能超过32个字符");
//
//        JSONObject data = new JSONObject();
//        data.element("scene", body.getScene());
//        data.element("page",body.getPage());
//        if (null != body.getWidth())
//            data.element("width", body.getWidth());
//        if (null != body.getAutoColor())
//            data.element("auto_color", body.getAutoColor());
//        if (null != body.getLineColor())
//            data.element("line_color", body.getLineColor());
//        String reqData = JSON.toJSONString(data);
//
//        File file = HttpClientUtil.sendPostByJsonToFile(reqUrl, reqData);
//        OssFile ossFile = ossService.upload(file, file.getName());
//        return ossFile;
//    }

}
