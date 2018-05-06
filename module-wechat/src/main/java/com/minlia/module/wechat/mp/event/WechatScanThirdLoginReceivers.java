package com.minlia.module.wechat.mp.event;

import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.mp.endpoint.WechatSecuritySocket;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.mp.service.LoginThirdPartyService;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
import com.minlia.module.websocket.body.ResponseMessage;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import me.chanjar.weixin.common.exception.WxErrorException;
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
    private UserService userService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private LoginThirdPartyService loginThirdPartyService;
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

            WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().unionId(wxMpUser.getUnionId()).type(WechatOpenidType.PUBLIC).build());
            if (null == wechatOpenAccount || null == wechatOpenAccount.getGuid()) {
                returnCode = SecurityApiCode.LOGIN_NOT_REGISTRATION+"";
                returnMessage = "未注册";
                payload = wechatOpenAccount.getWxCode();
            } else {
                returnCode = SecurityApiCode.LOGIN_SUCCESS+"";
                returnMessage = "ok";
                User user = userQueryService.queryByGuid(wechatOpenAccount.getGuid());
                payload = loginThirdPartyService.getLoginInfoByUser(user);
            }
            String sessionId = wxMessage.getEventKey().replace("qr_wsl_","");
            WechatSecuritySocket.webSocketMap.get(sessionId).getBasicRemote().sendObject(ResponseMessage.builder().code(returnCode).message(returnMessage).payload(payload).build());
        }
    }

}
