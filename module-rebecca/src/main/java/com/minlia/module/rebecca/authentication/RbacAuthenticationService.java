package com.minlia.module.rebecca.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.rebecca.risk.event.RiskLoginFailureEvent;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.event.SysLoginSuccessEvent;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.module.riskcontrol.service.DimensionService;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.authentication.service.AuthenticationService;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.config.SysSecurityConfig;
import com.minlia.modules.security.enumeration.LoginTypeEnum;
import com.minlia.modules.security.exception.AjaxBadCredentialsException;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.model.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author will
 * @date 8/14/17
 * 框架提供的抽象认证实现
 */
@Slf4j
@Component
//@Primary
public class RbacAuthenticationService implements AuthenticationService {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private SysSecurityConfig sysSecurityConfig;
    @Autowired
    private SysUserRegisterService userRegistrationService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Authentication authentication(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");
        LoginCredentials loginCredentials = (LoginCredentials) authentication.getPrincipal();

        String password = (String) authentication.getCredentials();
        String vcode = loginCredentials.getVcode();

        SysUserEntity userEntity = null;
        switch (loginCredentials.getType()) {
            case USERNAME:
                ApiAssert.hasLength(loginCredentials.getName(), SysUserCode.Message.USERNAME_NOT_NULL);
                userEntity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, loginCredentials.getName()));
                break;
            case CELLPHONE:
                ApiAssert.notNull(loginCredentials.getAreaCode(), SysUserCode.Message.AREA_CODE_NOT_NULL);
                ApiAssert.hasLength(loginCredentials.getName(), SysUserCode.Message.CELLPHONE_NOT_NULL);
                userEntity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery()
                        .eq(SysUserEntity::getCellphone, loginCredentials.getName())
                        .eq(SysUserEntity::getAreaCode, loginCredentials.getAreaCode()));

                if (StringUtils.isNotBlank(vcode)) {
                    if (Objects.isNull(userEntity)) {
                        Response response = captchaService.validity(loginCredentials.getAreaCode() + loginCredentials.getName(), vcode);
                        if (!response.isSuccess()) {
                            throw new DefaultAuthenticationException(response.getI18nCode());
                        }

                        userEntity = sysUserService.create(SysUserCro.builder()
                                .areaCode(loginCredentials.getAreaCode())
                                .cellphone(loginCredentials.getName())
                                .password(RandomStringUtils.randomAlphanumeric(16))
                                .roles(Sets.newHashSet())
                                .defaultRole("ROLE_MEMBER")
                                .build());

                        //获取用户上下文
                        UserContext userContext = loginService.getUserContext(userEntity, Objects.nonNull(loginCredentials.getCurrrole()) ? loginCredentials.getCurrrole() : userEntity.getDefaultRole());
                        checkDomain(userContext.getCurrdomain());

                        //登录成功事件
                        SysLoginSuccessEvent.publish(userEntity);
                        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
                    }
                }
                break;
            case EMAIL:
                ApiAssert.hasLength(loginCredentials.getName(), SysUserCode.Message.EMAIL_NOT_NULL);
                userEntity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, loginCredentials.getName()));
                break;
        }

        if (null == userEntity) {
            throw new UsernameNotFoundException("User not exists:");
        } else if (null != userEntity.getAccountEffectiveDate() && userEntity.getAccountEffectiveDate().isBefore(LocalDateTime.now())) {
            throw new AccountExpiredException("账号已过期");
        } else if (userEntity.getDisFlag()) {
            throw new DisabledException("账号已禁用");
        } else if (SysUserStatusEnum.TERMINATED.equals(userEntity.getStatus())) {
            throw new DefaultAuthenticationException(SysUserCode.Message.ALREADY_TERMINATED);
        } else if (userEntity.getLockFlag() && LocalDateTime.now().isBefore(userEntity.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(LocalDateTime.now(), userEntity.getLockTime()));
        }

        if (StringUtils.isNotBlank(password)) {
            //凭证有效期
            if (null != userEntity.getCredentialsEffectiveDate() && userEntity.getCredentialsEffectiveDate().isBefore(LocalDateTime.now())) {
                throw new CredentialsExpiredException("登陆凭证已过期");
            }
            if (!encoder.matches(password, userEntity.getPassword())) {
                //密码错误 锁定次数+1
                userEntity.setLockLimit(userEntity.getLockLimit() + NumberUtils.INTEGER_ONE);
                //如果超过3次 直接锁定
                if (userEntity.getLockLimit() >= sysSecurityConfig.getMaxValidationFailureTimes()) {
                    userEntity.setLockFlag(true);
                    //1、按错误次数累加时间   2、错误3次锁定一天
                    userEntity.setLockTime(LocalDateTime.now().plusDays(sysSecurityConfig.getLockedDays()));
                }
                sysUserService.update(userEntity, SysUserUpdateTypeEnum.PASSWORD_ERROR);
                throw new AjaxBadCredentialsException("Password error", userEntity.getLockLimit());
            }
        } else if (StringUtils.isNotBlank(vcode)) {
            Response response;
            if (LoginTypeEnum.CELLPHONE.equals(loginCredentials.getType())) {
                response = captchaService.validity(userEntity.getAreaCode() + userEntity.getCellphone(), vcode);
            } else {
                response = captchaService.validity(userEntity.getEmail(), vcode);
            }
            if (!response.isSuccess()) {
                throw new DefaultAuthenticationException(response.getI18nCode());
            }
        }

        //清除缓存登陆失败记录 TODO
        dimensionService.cleanCountWithRedis(new RiskLoginFailureEvent(), new String[]{RiskLoginFailureEvent.IP}, RiskLoginFailureEvent.TIME);

        //更新用户信息
        userEntity.setLockFlag(Boolean.FALSE);
        userEntity.setLockLimit(NumberUtils.INTEGER_ZERO);
        userEntity.setLastLoginTime(LocalDateTime.now());
        userEntity.setLastLoginIp(RequestIpUtils.getClientIP());
        sysUserService.update(userEntity, SysUserUpdateTypeEnum.SYSTEM_UPDATE);

        //获取用户上下文
        UserContext userContext = loginService.getUserContext(userEntity, Objects.nonNull(loginCredentials.getCurrrole()) ? loginCredentials.getCurrrole() : userEntity.getDefaultRole());
        checkDomain(userContext.getCurrdomain());

        //登录成功事件
        SysLoginSuccessEvent.publish(userEntity);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }


    /**
     * 判断域名是否匹配
     *
     * @param currdomain
     */
    public void checkDomain(String currdomain) {
        if (StringUtils.isNotBlank(currdomain)) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
            try {
                URL url = new URL(servletRequest.getRequestURL().toString());
                if (!currdomain.contains(url.getHost())) {
                    throw new DefaultAuthenticationException(SecurityCode.Exception.AUTH_METHOD_NOT_SUPPORTED);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

}