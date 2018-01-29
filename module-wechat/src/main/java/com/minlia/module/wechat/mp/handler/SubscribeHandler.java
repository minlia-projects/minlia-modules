package com.minlia.module.wechat.mp.handler;

import com.minlia.module.wechat.miniapp.service.WechatInfoService;
import com.minlia.module.wechat.mp.builder.TextBuilder;
import com.minlia.module.wechat.mp.event.WechatSubscribeEvent;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private WechatInfoService wechatInfoService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        //获取微信用户基本信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        if (null != wxMpUser) {
            //保存公众号相关信息
            wechatInfoService.save(wxMpUser);

            //发布关注成功事件
            WechatSubscribeEvent.onSubscribe(wxMessage);
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, wxMpService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

}
