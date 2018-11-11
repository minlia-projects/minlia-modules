package com.minlia.module.wechat.mp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.ma.bean.qo.WechatOpenAccountQO;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.bean.domain.WechatMaUser;
import com.minlia.module.wechat.ma.bean.domain.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatMaUserService;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
import com.minlia.module.wechat.mp.body.BindWxRequestBody;
import com.minlia.module.wechat.mp.body.LoginWechatRequestBody;
import com.minlia.module.wechat.mp.constant.WechatMpCode;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.to.UserRegistrationTO;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import com.minlia.modules.rbac.service.LoginService;
import com.minlia.modules.rbac.service.UserQueryService;
import com.minlia.modules.rbac.service.UserRegistrationService;
import com.minlia.modules.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class LoginThirdPartyServiceImpl implements LoginThirdPartyService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private WechatMaUserService wechatMaUserService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public Response loginByWxMpCode(LoginWechatRequestBody body) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(body.getCode());
        ApiAssert.hasLength(wxMpOAuth2AccessToken.getUnionId(), WechatMpCode.Message.UNION_ID_NOT_NULL);
        ApiAssert.hasLength(wxMpOAuth2AccessToken.getOpenId(), WechatMpCode.Message.OPEN_ID_NOT_NULL);
        return this.login(WechatOpenidType.MINIAPP,wxMpOAuth2AccessToken.getUnionId(),wxMpOAuth2AccessToken.getOpenId(),body.getType(),body.getCode());
    }

    @Override
    public Response loginByWxMaCode(LoginWechatRequestBody body) {
        WxMaService wxMaService = wechatMaService.getWxMaService(body.getType());
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService,body.getCode());

        log.info("SessionKey：{}", sessionResult.getSessionKey());
        log.info("EncryptedData：{}", body.getEncryptedData());
        log.info("Iv：{}", body.getIv());

        WxMaUserInfo wxMaUserInfo = null;
        try {
            wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionResult.getSessionKey(),body.getEncryptedData(),body.getIv());
            log.info("解密信息：{}", wxMaUserInfo.toString());
        } catch (Exception e) {
            log.error("小程序用户信息解密失败", e);
            ApiAssert.state(true, "小程序用户信息解密失败");
        }
        ApiAssert.hasLength(wxMaUserInfo.getUnionId(), WechatMpCode.Message.UNION_ID_NOT_NULL);
        ApiAssert.hasLength(wxMaUserInfo.getOpenId(), WechatMpCode.Message.OPEN_ID_NOT_NULL);
        //保存小程序用户信息
        wechatMaUserService.update(wxMaUserInfo, body.getCode(), null);
        return this.login(WechatOpenidType.MINIAPP,wxMaUserInfo.getUnionId(),wxMaUserInfo.getOpenId(),body.getType(),body.getCode());
    }

    private Response login(WechatOpenidType wechatOpenidType, String unionId, String openId, String openidSubitem, String wxCode){
        WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQO.builder().type(wechatOpenidType).openId(openId).build());

        if (null == wechatOpenAccount) {
            //创建openId信息
            wechatOpenAccount = new WechatOpenAccount();
            wechatOpenAccount.setUnionId(unionId);
            wechatOpenAccount.setOpenId(openId);
            wechatOpenAccount.setWxCode(wxCode);
            wechatOpenAccount.setType(WechatOpenidType.MINIAPP);
            wechatOpenAccount.setSubitem(openidSubitem);

            //判断是否用其他openId登录过 TODO
            List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryByUnionIdAndGuidNotNull(unionId);
            if (CollectionUtils.isEmpty(wechatOpenAccounts)) {
                wechatOpenAccountService.create(wechatOpenAccount);
                return Response.failure("未注册");
            } else {
                wechatOpenAccount.setGuid(wechatOpenAccounts.get(0).getGuid());
                wechatOpenAccountService.create(wechatOpenAccount);
                User user = userQueryService.queryOne(UserQO.builder().guid(wechatOpenAccount.getGuid()).build());
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            }
        } else {
            //更新wxCode
            wechatOpenAccount.setWxCode(wxCode);
            wechatOpenAccountService.update(wechatOpenAccount);

            if (null != wechatOpenAccount.getGuid()) {
                User user = userQueryService.queryOne(UserQO.builder().guid(wechatOpenAccount.getGuid()).build());
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                return Response.failure("未注册");
            }
        }
    }

    @Override
    public Response bindByWxma(BindWxRequestBody body) {
        //绑定前会先调登陆保存
        //查询CODE是否存在
        WechatOpenAccount codeAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQO.builder().wxCode(body.getWxCode()).build());
        ApiAssert.notNull(codeAccount, "wxCode未判断是否绑定");
        ApiAssert.isNull(codeAccount.getGuid(), WechatMpCode.Message.OPENID_ALREADY_BIND);

        //根据手机号码查询用户是否存在
        User user = userQueryService.queryOne(UserQO.builder().username(body.getUsername()).build());
        //不存在就注册
        if (null != user) {
            //是否已绑定其他手机号码
            WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQO.builder().guid(user.getGuid()).type(WechatOpenidType.MINIAPP).wxCode(body.getWxCode()).build());
            ApiAssert.isNull( wechatOpenAccount, WechatMpCode.Message.OPENID_ALREADY_BIND);
        } else {
            UserRegistrationTO userRegistrationTO = new UserRegistrationTO();
            userRegistrationTO.setType(RegistrationMethodEnum.CELLPHONE);
            userRegistrationTO.setCellphone(body.getUsername());
            userRegistrationTO.setCode(body.getSecurityCode());
            userRegistrationTO.setPassword(RandomStringUtils.randomAlphabetic(6));
            user = userRegistrationService.registration(userRegistrationTO);

            //绑定用户GUID
            WechatMaUser wechatMaUser = wechatMaUserService.queryOne(WechatMaUserQO.builder().code(body.getWxCode()).build());
            wechatMaUser.setGuid(user.getGuid());
            wechatMaUserService.update(wechatMaUser);
        }
        return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
    }

}
