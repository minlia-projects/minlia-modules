package com.minlia.module.wechat.mp.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatOpenAccountService;
import com.minlia.module.wechat.mp.body.BindWxRequestBody;
import com.minlia.module.wechat.mp.body.LoginWechatRequestBody;
import com.minlia.module.wechat.mp.constant.WechatMpCode;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserRegistrationTO;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.enumeration.RegistrationMethodEnum;
import com.minlia.modules.rbac.service.LoginService;
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
    private UserRegistrationService userRegistrationService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public Response loginByWxMpCode(LoginWechatRequestBody body) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(body.getCode());
        return this.login(WechatOpenidType.MINIAPP,wxMpOAuth2AccessToken.getUnionId(),wxMpOAuth2AccessToken.getOpenId(),body.getType(),body.getCode());
    }

    @Override
    public Response loginByWxMaCode(LoginWechatRequestBody body) {
        //远程从微信获取小程序信息
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(body.getType(),body.getCode());
        ApiAssert.notNull(sessionResult.getOpenid(), WechatMpCode.Message.GET_SESSION_FAILURE);
        return this.login(WechatOpenidType.MINIAPP,sessionResult.getUnionid(),sessionResult.getOpenid(),body.getType(),body.getCode());
    }

    private Response login(WechatOpenidType wechatOpenidType,String unionId,String openId,String openidSubitem,String wxCode){
        WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().type(wechatOpenidType).openId(openId).build());

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
                User user = userQueryService.queryOne(UserQueryRequestBody.builder().guid(wechatOpenAccount.getGuid()).build());
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            }
        } else {
            //更新wxCode
            wechatOpenAccount.setWxCode(wxCode);
            wechatOpenAccountService.update(wechatOpenAccount);

            if (null != wechatOpenAccount.getGuid()) {
                User user = userQueryService.queryOne(UserQueryRequestBody.builder().guid(wechatOpenAccount.getGuid()).build());
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                return Response.failure("未注册");
            }
        }
    }

    @Override
    public Response bindByWxma(BindWxRequestBody body) {
        //根据手机号码查询用户是否存在
        User user = userQueryService.queryOne(UserQueryRequestBody.builder().username(body.getUsername()).build());
        //明文密码
        String rawPassword = RandomStringUtils.randomAlphabetic(6);
        //不存在就注册
        if (null == user) {
            UserRegistrationTO userRegistrationRequestBody = new UserRegistrationTO();
            userRegistrationRequestBody.setType(RegistrationMethodEnum.CELLPHONE);
            userRegistrationRequestBody.setUsername(body.getUsername());
            userRegistrationRequestBody.setCellphone(body.getUsername());
            userRegistrationRequestBody.setCode(body.getSecurityCode());
            userRegistrationRequestBody.setPassword(rawPassword);
            user = userRegistrationService.registration(userRegistrationRequestBody);
        }

        //查询CODE是否存在,CODE必须唯一
        WechatOpenAccount codeAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().wxCode(body.getWxCode()).build());
        ApiAssert.notNull(null == codeAccount, "wxCode未判断是否绑定");

        WechatOpenAccount wechatOpenAccountByUser = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().guid(user.getGuid()).type(WechatOpenidType.MINIAPP).wxCode(body.getWxCode()).build());

        if (null == wechatOpenAccountByUser) {
            if (null == codeAccount.getGuid()) {
                //userId未绑定openId且 userId为空
                codeAccount.setGuid(user.getGuid());
                wechatOpenAccountService.update(codeAccount);
                return Response.success(loginService.getLoginInfoByUser(user, SecurityConstant.ROLE_USER_CODE));
            } else {
                return Response.failure(WechatMpCode.Message.OPENID_ALREADY_BIND);
            }
        } else {
            return Response.failure(WechatMpCode.Message.OPENID_ALREADY_BIND);
        }
    }

}
