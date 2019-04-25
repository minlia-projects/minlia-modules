package com.minlia.module.wechat.mp.handler;

import com.minlia.module.wechat.login.ro.WechatUserQO;
import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.login.service.WechatUserService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // TODO 可以更新本地数据库为取消关注状态
        WechatUser wechatUser = wechatUserService.queryOne(WechatUserQO.builder().openId(openId).type(WechatOpenidType.PUBLIC).isSubscribe(true).build());
        if(null != wechatUser){
            wechatUser.setIsSubscribe(false);
            wechatUserService.update(wechatUser);
        }
        return null;
    }

}
