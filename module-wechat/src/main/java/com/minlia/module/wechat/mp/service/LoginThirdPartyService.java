package com.minlia.module.wechat.mp.service;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.wechat.mp.body.LoginWechatRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.openapi.registration.body.UserBindWxRequestBody;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Transactional
public interface LoginThirdPartyService {

    StatefulBody loginByWxMpCode(LoginWechatRequestBody body) throws WxErrorException;

    StatefulBody loginByWxMaCode(LoginWechatRequestBody body);

    StatefulBody bindByWxma(UserBindWxRequestBody body);

    HashMap getLoginInfoByUser(User user);

}
