package com.minlia.module.wechat.mp.event;

import com.minlia.module.websocket.body.ResponseMessage;
import com.minlia.module.wechat.ma.bean.qo.WechatOpenAccountQO;
import com.minlia.module.wechat.ma.bean.domain.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
import com.minlia.module.wechat.mp.endpoint.WechatSecuritySocket;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.service.LoginService;
import com.minlia.modules.rbac.service.UserQueryService;
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
public class WechatScanThirdLoginReceivers {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

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

            WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQO.builder().unionId(wxMpUser.getUnionId()).type(WechatOpenidType.PUBLIC).build());
            if (null == wechatOpenAccount || null == wechatOpenAccount.getGuid()) {
                returnCode = "100102";
                returnMessage = "未注册";
                payload = wechatOpenAccount.getWxCode();
            } else {
                returnCode = "100103";
                returnMessage = "ok";
                User user = userQueryService.queryOne(UserQO.builder().build().builder().guid(wechatOpenAccount.getGuid()).build());
                payload = loginService.getLoginInfoByUser(user, user.getDefaultRole());
            }
            String sessionId = wxMessage.getEventKey().replace("qr_wsl_","");
            WechatSecuritySocket.webSocketMap.get(sessionId).getBasicRemote().sendObject(ResponseMessage.builder().code(returnCode).message(returnMessage).payload(payload).build());
        }
    }

}
