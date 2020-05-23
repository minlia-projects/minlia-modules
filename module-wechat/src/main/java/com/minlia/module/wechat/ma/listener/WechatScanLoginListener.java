package com.minlia.module.wechat.ma.listener;

import com.minlia.module.websocket.body.ResponseMessage;
import com.minlia.module.wechat.login.endpoint.WechatSecuritySocket;
import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.login.ro.WechatUserQO;
import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.mp.event.WechatScanEvent;
import com.minlia.module.wechat.mp.event.WechatSubscribeEvent;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.service.LoginService;
import com.minlia.modules.rebecca.service.UserQueryService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by garen on 2017/11/27.
 */
@Component
public class WechatScanLoginListener {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private WechatUserService wechatUserService;

    @EventListener
    public void onScan(WechatScanEvent event) throws Exception {
        WxMpXmlMessage wxMessage = (WxMpXmlMessage) event.getSource();
        this.onLogin(wxMessage);
    }

    @EventListener
    public void onSubscribe(WechatSubscribeEvent event) throws Exception {
        WxMpXmlMessage wxMessage = (WxMpXmlMessage) event.getSource();
        this.onLogin(wxMessage);
    }

    private void onLogin(WxMpXmlMessage wxMessage) throws WxErrorException, IOException, EncodeException {
        if (wxMessage.getEventKey().startsWith("qr_wsl_")) {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());

            String returnCode;
            String returnMessage;
            Object payload;

            WechatUser wechatUser = wechatUserService.queryOne(WechatUserQO.builder().unionId(wxMpUser.getUnionId()).type(WechatOpenidType.PUBLIC).build());
            if (null == wechatUser || null == wechatUser.getGuid()) {
                returnCode = "100102";
                returnMessage = "未注册";
                payload = wechatUser.getWxCode();
            } else {
                returnCode = "100103";
                returnMessage = "ok";
                User user = userQueryService.queryOne(UserQO.builder().build().builder().guid(wechatUser.getGuid()).build());
                payload = loginService.getLoginInfoByUser(user, user.getDefaultRole());
            }
            String sessionId = wxMessage.getEventKey().replace("qr_wsl_","");
            WechatSecuritySocket.webSocketMap.get(sessionId).getBasicRemote().sendObject(ResponseMessage.builder().code(returnCode).message(returnMessage).payload(payload).build());
        }
    }

}
