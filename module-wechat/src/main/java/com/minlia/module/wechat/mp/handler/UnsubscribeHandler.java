package com.minlia.module.wechat.mp.handler;

import com.minlia.module.wechat.ma.bean.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
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
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        // TODO 可以更新本地数据库为取消关注状态
        WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().openId(openId).type(WechatOpenidType.PUBLIC).isSubscribe(true).build());
        if(null != wechatOpenAccount){
            wechatOpenAccount.setIsSubscribe(false);
            wechatOpenAccountService.update(wechatOpenAccount);
        }
        return null;
    }

}
