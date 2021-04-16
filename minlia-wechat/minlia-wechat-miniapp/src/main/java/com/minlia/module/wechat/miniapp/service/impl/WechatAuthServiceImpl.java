package com.minlia.module.wechat.miniapp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.wechat.miniapp.bean.WechatMaLoginRo;
import com.minlia.module.wechat.miniapp.config.WxMaConfiguration;
import com.minlia.module.wechat.miniapp.constant.WechatMiniappCode;
import com.minlia.module.wechat.miniapp.entity.WxMaUserInfoEntity;
import com.minlia.module.wechat.miniapp.service.WechatAuthService;
import com.minlia.module.wechat.miniapp.service.WxMaUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author garen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatAuthServiceImpl implements WechatAuthService {

    private final LoginService loginService;
    private final WxMaUserInfoService wxMaUserInfoService;

    @Override
    public Response login(WechatMaLoginRo ro) {
        final WxMaService wxService = WxMaConfiguration.getMaService(ro.getAppid());
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(ro.getCode());
            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(session.getSessionKey(), ro.getUserEncryptedData(), ro.getUserIv());
            WxMaPhoneNumberInfo phoneNumberInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(), ro.getPhoneEncryptedData(), ro.getPhoneIv());

            //保存信息
            WxMaUserInfoEntity userInfoEntity = wxMaUserInfoService.save(session, userInfo, phoneNumberInfo);
//            ApiAssert.state(Objects.nonNull(userInfoEntity.getUid()), WechatMiniappCode.Message.UNREGISTERED);

            //获取登陆信息
            return Response.success(loginService.getLoginInfoByCellphone(userInfoEntity.getPhoneNumber()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failure("500", "登陆失败:" + e.getMessage());
        }
    }

}