package com.minlia.module.wechat.mp.handler;

import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.mp.builder.TextBuilder;
import com.minlia.module.wechat.mp.event.WechatScanEvent;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScanHandler extends AbstractHandler {

    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        //获取微信用户基本信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        wechatUserService.save(wxMpUser);

        if (StringUtils.isEmpty(wxMessage.getEventKey())){
            new TextBuilder().build("感谢关注", wxMessage,wxMpService);
        }else{
            //发布扫码成功事件
            WechatScanEvent.onScan(wxMessage);
        }

        return null;
    }
}
