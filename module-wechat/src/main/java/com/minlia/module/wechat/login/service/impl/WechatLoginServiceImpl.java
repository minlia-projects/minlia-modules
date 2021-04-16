package com.minlia.module.wechat.login.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.wechat.login.bean.WechatBindRO;
import com.minlia.module.wechat.login.bean.WechatLoginRO;
import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.login.ro.WechatUserQO;
import com.minlia.module.wechat.login.service.WechatLoginService;
import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.ma.bean.entity.WechatMaUser;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.constant.WechatMaBibleConstants;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.event.WechatMaEventPublisher;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatMaUserService;
import com.minlia.module.wechat.mp.constant.WechatMpCode;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WechatLoginServiceImpl implements WechatLoginService {

    private final WxMpService wxMpService;
    private final UserService userService;
    private final LoginService loginService;
    private final WechatMaService wechatMaService;
    private final UserQueryService userQueryService;
    private final BibleItemService bibleItemService;
    private final WechatUserService wechatUserService;
    private final WechatMaUserService wechatMaUserService;
    private final UserRegistrationService userRegistrationService;

    public WechatLoginServiceImpl(WxMpService wxMpService, UserService userService, LoginService loginService, WechatMaService wechatMaService, UserQueryService userQueryService, BibleItemService bibleItemService, WechatUserService wechatUserService, WechatMaUserService wechatMaUserService, UserRegistrationService userRegistrationService) {
        this.wxMpService = wxMpService;
        this.userService = userService;
        this.loginService = loginService;
        this.wechatMaService = wechatMaService;
        this.userQueryService = userQueryService;
        this.bibleItemService = bibleItemService;
        this.wechatUserService = wechatUserService;
        this.wechatMaUserService = wechatMaUserService;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public Response loginByWxMpCode(WechatLoginRO ro) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(ro.getCode());
        ApiAssert.hasLength(wxMpOAuth2AccessToken.getUnionId(), WechatMpCode.Message.UNION_ID_NOT_NULL);
        ApiAssert.hasLength(wxMpOAuth2AccessToken.getOpenId(), WechatMpCode.Message.OPEN_ID_NOT_NULL);
        return this.login(WechatOpenidType.PUBLIC, wxMpOAuth2AccessToken.getUnionId(), wxMpOAuth2AccessToken.getOpenId(), ro.getType(), ro.getCode());
    }

    @Override
    public Response loginByWxMaCode(WechatLoginRO ro) {
        //小程序用户信息
        WxMaService wxMaService = wechatMaService.getWxMaService(ro.getType());
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService, ro.getCode());
        WxMaUserInfo wxMaUserInfo;
        try {
            wxMaUserInfo = wechatMaService.decrypt(wxMaService, sessionResult.getSessionKey(), ro.getEncryptedData(), ro.getIv());
        } catch (Exception e) {
            log.error("小程序用户信息解密失败-------------------------------------------------");
            wxMaUserInfo = new WxMaUserInfo();
            wxMaUserInfo.setUnionId(sessionResult.getUnionid());
            wxMaUserInfo.setOpenId(sessionResult.getOpenid());
            wxMaUserInfo.setWatermark(new WxMaUserInfo.Watermark());
        }
        wechatMaUserService.update(wxMaUserInfo, ro.getCode(), null);
        return this.login(WechatOpenidType.MINIAPP, wxMaUserInfo.getUnionId(), wxMaUserInfo.getOpenId(), ro.getType(), ro.getCode());
    }

    private Response login(WechatOpenidType wechatOpenidType, String unionId, String openId, String openidSubitem, String wxCode){
        log.error("小程序login-------------------------------------------------");

        WechatUser wechatUser = wechatUserService.queryOne(WechatUserQO.builder().type(wechatOpenidType).openId(openId).build());
        if (null == wechatUser) {
            log.error("小程序login wechatUser is null-------------------------------------------------");

            //创建openId信息
            wechatUser = new WechatUser();
            wechatUser.setUnionId(unionId);
            wechatUser.setOpenId(openId);
            wechatUser.setWxCode(wxCode);
            wechatUser.setType(wechatOpenidType);
            wechatUser.setSubitem(openidSubitem);

            String registerFlag = bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_REGISTER_FLAG);
            if (null != registerFlag && BooleanUtils.toBoolean(registerFlag)) {
                log.info("登陆创建默认用户---------------------------");
                String username = NumberGenerator.generatorByYMDHMSS("C",5);
                String rawPassword = RandomStringUtils.randomAlphabetic(8);
//                User user = userService.create(UserCTO.builder().method(RegistrationMethodEnum.USERNAME).username(username).password(rawPassword).roles(Sets.newHashSet(2L)).defaultRole(2L).build());
                User user = userService.create(UserCTO.builder().username(username).password(rawPassword).defaultRole(SecurityConstant.ROLE_USER_CODE).roles(Sets.newHashSet(SecurityConstant.ROLE_USER_CODE)).build());
                wechatUser.setGuid(user.getGuid());
                wechatUserService.create(wechatUser);
                WechatMaEventPublisher.onAuthorized(wechatUser);
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                //判断是否用其他openId登录过 TODO
                if (StringUtils.isNotBlank(unionId)) {
                    List<WechatUser> wechatUsers = wechatUserService.queryByUnionIdAndGuidNotNull(unionId);
                    if (CollectionUtils.isEmpty(wechatUsers)) {
                        wechatUserService.create(wechatUser);
                        return Response.success(UserCode.Message.UNREGISTERED);
                    } else {
                        wechatUser.setGuid(wechatUsers.get(0).getGuid());
                        wechatUserService.create(wechatUser);
                        User user = userQueryService.queryOne(UserQO.builder().guid(wechatUser.getGuid()).build());
                        WechatMaEventPublisher.onAuthorized(wechatUser);
                        return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
                    }
                } else {
                    log.error("小程序login unionId is null-------------------------------------------------");
                    wechatUserService.create(wechatUser);
                    return Response.success(UserCode.Message.UNREGISTERED);
                }
            }
        } else {
            log.error("小程序login update-------------------------------------------------");
            wechatUser.setWxCode(wxCode);
            wechatUserService.update(wechatUser);
            log.error("小程序login update end-------------------------------------------------");

            if (null != wechatUser.getGuid()) {
                log.error("小程序login guid-------------------------------------------------");
                User user = userQueryService.queryOne(UserQO.builder().guid(wechatUser.getGuid()).build());
                WechatMaEventPublisher.onAuthorized(wechatUser);
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                return Response.success(UserCode.Message.UNREGISTERED);
            }
        }
    }

    private Response login2222(WechatOpenidType wechatOpenidType, String unionId, String openId, String openidSubitem, String wxCode) {
        log.debug("小程序授权登录");
        log.debug("根据类型[{}] 和 openid [{}] 查询出微信用户", wechatOpenidType, openId);
        WechatUser wechatUser = wechatUserService.queryOne(WechatUserQO.builder().type(wechatOpenidType).openId(openId).build());
        log.debug("查询到的微信用户: {}", wechatUser);
        User user;

        if (StringUtils.isNotBlank(unionId)) {
            log.debug("当有联盟号时");
            log.debug("UnionId: {}", unionId);
            List<WechatUser> wechatUsers = this.wechatUserService.queryByUnionIdAndGuidNotNull(unionId);
            if (CollectionUtils.isEmpty(wechatUsers)) {
                log.debug("通过微信联盟ID查找不到微信用户信息");
                this.wechatUserService.create(wechatUser);
                return Response.success(com.minlia.modules.rebecca.constant.UserCode.Message.UNREGISTERED);
            } else {
                log.debug("通过微信联盟ID查找到微信用户信息: 共: {}", wechatUsers.size());
                wechatUser.setGuid((wechatUsers.get(0)).getGuid());
                WechatUser created = this.wechatUserService.create(wechatUser);
                user = this.userQueryService.queryOne(UserQO.builder().guid(wechatUser.getGuid()).build());
                WechatMaEventPublisher.onAuthorized(created);
                return Response.success(this.loginService.loginWithRole(user, SecurityConstant.ROLE_USER_CODE));
            }
        }

        if (null == wechatUser) {
            //当无法查询到微信用户时自动创建一个微信用户
            wechatUser = createWechatUserAccount(wechatOpenidType, unionId, openId, openidSubitem, wxCode);
            user = createSystemUser(wechatUser);
        } else {
            //一个已授权登录过的老用户
            user = updateWechatUserAccount(wxCode, wechatUser);
        }

//        } else {
//            log.info("小程序login unionId is null-------------------------------------------------");
//            WechatUser created = this.wechatUserService.create(wechatUser);
//            WechatMaEventPublisher.onAuthorized(created);
//            return Response.success(com.minlia.modules.rebecca.constant.UserCode.Message.UNREGISTERED);
        WechatMaEventPublisher.onAuthorized(wechatUser);
        return Response.success(user);
    }

    private User updateWechatUserAccount(String wxCode, WechatUser wechatUser) {
        wechatUser.setWxCode(wxCode);
        WechatUser updated = wechatUserService.update(wechatUser);
        WechatMaEventPublisher.onUpdated(updated);
        User user = userQueryService.queryOne(UserQO.builder().guid(wechatUser.getGuid()).build());
        return user;
    }

    /**
     * 当系统配置允许创建系统用户时自动创建系统用户, 并与当前微信用户绑定
     *
     * @param wechatUser
     */
    private User createSystemUser(WechatUser wechatUser) {
        log.debug("创建系统用户");
        Boolean allowAutoRegistration = getAllowAutoRegistrationValue();
        log.debug("系统配置(ALLOW_AUTO_REGISTRATION)是否允许自动为微信登录用户注册新账户: {}", allowAutoRegistration);
        User user;
        if (allowAutoRegistration) {
            log.info("自动创建一个已授权登录的用户");
            String username = NumberGenerator.generatorByYMDHMSS("C", 5);
            String rawPassword = RandomStringUtils.randomAlphabetic(8);
            user = this.userService.create(UserCTO.builder().username(username).defaultRole(SecurityConstant.ROLE_USER_CODE).roles(Sets.newHashSet(SecurityConstant.ROLE_USER_CODE)).password(rawPassword).build());
            log.debug("已创建系统用户 {}", user);

            wechatUser.setGuid(user.getGuid());
            log.debug("绑定系统用用户关系: {}", user.getGuid());
            WechatUser created = this.wechatUserService.update(wechatUser);
            log.debug("更新微信用户: {}", created);
            log.debug("创建系统用户流程结束");
//                return Response.success(this.loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            //不允许自动创建用户
//            WechatMaEventPublisher.onAuthorized(user);
            return user;
        } else {
            return null;
        }
    }

    private WechatUser createWechatUserAccount(WechatOpenidType wechatOpenidType, String unionId, String openId, String openidSubitem, String wxCode) {
        WechatUser wechatUser;
        log.debug("微信用户为空, 第一次使用微信授权登录");
        wechatUser = new WechatUser();
        wechatUser.setUnionId(unionId);
        wechatUser.setOpenId(openId);
        wechatUser.setWxCode(wxCode);
        wechatUser.setType(wechatOpenidType);
        wechatUser.setSubitem(openidSubitem);
        log.debug("首先创建一个新的微信用户实例: {}", wechatUser);
        WechatUser created = this.wechatUserService.create(wechatUser);
        log.debug("已创建的微信用户: {}", created);
        return created;
    }

    private Boolean getAllowAutoRegistrationValue() {
        String allowAutoRegistration = this.bibleItemService.get("WECHAT_MA", "ALLOW_AUTO_REGISTRATION");
        //向后版本兼容
        if (StringUtils.isEmpty(allowAutoRegistration)) {
            allowAutoRegistration = this.bibleItemService.get("WECHAT_MA", "REGISTER_FLAG");
        }
        if (null != allowAutoRegistration && BooleanUtils.toBoolean(allowAutoRegistration)) {
            return BooleanUtils.toBoolean(allowAutoRegistration);
        }
        return Boolean.FALSE;
    }


    @Override
    @Transactional
    public Response bindByWxma(WechatBindRO ro) {
        //绑定前会先调登陆保存
        //查询CODE是否存在
        WechatUser codeAccount = wechatUserService.queryOne(WechatUserQO.builder().wxCode(ro.getWxCode()).build());
        ApiAssert.notNull(codeAccount, "wxCode未判断是否绑定");
        ApiAssert.isNull(codeAccount.getGuid(), WechatMpCode.Message.OPENID_ALREADY_BIND);

        //根据手机号码查询用户是否存在
        User user = userQueryService.queryOne(UserQO.builder().username(ro.getUsername()).build());
        //不存在就注册
        if (null != user) {
            //是否已绑定其他手机号码
            WechatUser wechatUser = wechatUserService.queryOne(WechatUserQO.builder().guid(user.getGuid()).type(WechatOpenidType.MINIAPP).wxCode(ro.getWxCode()).build());
            ApiAssert.isNull(wechatUser, WechatMpCode.Message.OPENID_ALREADY_BIND);
        } else {
            UserRegistrationTO userRegistrationTO = new UserRegistrationTO();
            userRegistrationTO.setType(RegistrationMethodEnum.CELLPHONE);
            userRegistrationTO.setCellphone(ro.getUsername());
            userRegistrationTO.setCode(ro.getSecurityCode());
            userRegistrationTO.setPassword(RandomStringUtils.randomAlphabetic(6));
            user = userRegistrationService.registration(userRegistrationTO);

            //绑定用户GUID
            WechatMaUser wechatMaUser = wechatMaUserService.queryOne(WechatMaUserQO.builder().code(ro.getWxCode()).build());
            wechatMaUser.setGuid(user.getGuid());
            wechatMaUserService.update(wechatMaUser);
        }
        return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
    }

}