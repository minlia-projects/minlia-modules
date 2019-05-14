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
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatMaUserService;
import com.minlia.module.wechat.mp.constant.WechatMpCode;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.to.UserCTO;
import com.minlia.modules.rbac.bean.to.UserRegistrationTO;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import com.minlia.modules.rbac.service.LoginService;
import com.minlia.modules.rbac.service.UserQueryService;
import com.minlia.modules.rbac.service.UserRegistrationService;
import com.minlia.modules.rbac.service.UserService;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WechatLoginServiceImpl implements WechatLoginService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private BibleItemService bibleItemService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private WechatMaUserService wechatMaUserService;
    @Autowired
    private UserRegistrationService userRegistrationService;

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
                User user = userService.create(UserCTO.builder().method(RegistrationMethodEnum.USERNAME).username(username).password(rawPassword).roles(Sets.newHashSet(2L)).defaultRole(2L).build());
                wechatUser.setGuid(user.getGuid());
                wechatUserService.create(wechatUser);
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                //判断是否用其他openId登录过 TODO
                if (StringUtils.isNotBlank(unionId)) {
                    List<WechatUser> wechatUsers = wechatUserService.queryByUnionIdAndGuidNotNull(unionId);
                    if (CollectionUtils.isEmpty(wechatUsers)) {
                        wechatUserService.create(wechatUser);
                        return Response.success(RebaccaCode.Message.UNREGISTERED);
                    } else {
                        wechatUser.setGuid(wechatUsers.get(0).getGuid());
                        wechatUserService.create(wechatUser);
                        User user = userQueryService.queryOne(UserQO.builder().guid(wechatUser.getGuid()).build());
                        return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
                    }
                } else {
                    log.error("小程序login unionId is null-------------------------------------------------");
                    wechatUserService.create(wechatUser);
                    return Response.success(RebaccaCode.Message.UNREGISTERED);
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
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                return Response.success(RebaccaCode.Message.UNREGISTERED);
            }
        }
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