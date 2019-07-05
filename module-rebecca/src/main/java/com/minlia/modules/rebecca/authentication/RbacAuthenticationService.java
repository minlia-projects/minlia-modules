package com.minlia.modules.rebecca.authentication;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.http.NetworkUtil;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.event.LoginSuccessEvent;
import com.minlia.modules.rebecca.mapper.UserMapper;
import com.minlia.modules.rebecca.service.LoginService;
import com.minlia.modules.rebecca.service.UserService;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.authentication.service.AuthenticationService;
import com.minlia.modules.security.enumeration.LoginMethodEnum;
import com.minlia.modules.security.exception.AjaxBadCredentialsException;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.model.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
@Component
@Primary
public class RbacAuthenticationService implements AuthenticationService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Authentication authentication(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");
        LoginCredentials loginCredentials = (LoginCredentials) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String currrole = loginCredentials.getCurrrole();
        String captcha = loginCredentials.getCaptcha();

        User user = null;
        switch (loginCredentials.getMethod()) {
            case USERNAME:
                ApiAssert.hasLength(loginCredentials.getUsername(), UserCode.Message.USERNAME_NOT_NULL);
                user = userMapper.queryOne(UserQO.builder().username(loginCredentials.getUsername()).build());
                break;
            case CELLPHONE:
                ApiAssert.hasLength(loginCredentials.getCellphone(), UserCode.Message.CELLPHONE_NOT_NULL);
                user = userMapper.queryOne(UserQO.builder().cellphone(loginCredentials.getCellphone()).build());
                break;
            case EMAIL:
                ApiAssert.hasLength(loginCredentials.getEmail(), UserCode.Message.EMAIL_NOT_NULL);
                user = userMapper.queryOne(UserQO.builder().email(loginCredentials.getEmail()).build());
                break;
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not exists:");
        }
        if (StringUtils.isNotBlank(password) && !encoder.matches(password, user.getPassword())) {
            //密码错误 锁定次数+1
            user.setLockLimit(user.getLockLimit() + NumberUtils.INTEGER_ONE);
            //如果超过3次 直接锁定
            if (user.getLockLimit() > 2) {
                user.setLocked(true);
                //1、按错误次数累加时间   2、错误3次锁定一天
                user.setLockTime(DateUtils.addMinutes(new Date(), (int) Math.pow(user.getLockLimit() - 3, 3)));
            }
            userService.update(user, UserUpdateTypeEcnum.PASSWORD_ERROR);
            throw new AjaxBadCredentialsException("Password error", user.getLockLimit());
        }
        if (StringUtils.isNotBlank(captcha)) {
            if (LoginMethodEnum.CELLPHONE.equals(loginCredentials.getMethod())) {
                captchaService.validityByCellphone(user.getCellphone(), captcha);
            } else {
                captchaService.validityByCellphone(user.getEmail(), captcha);
            }
        }

        if (null != user.getAccountEffectiveDate() && user.getAccountEffectiveDate().before(new Date())) {
            throw new AccountExpiredException("账号已过期");
        } else if (!user.getEnabled()) {
            throw new DisabledException("账号已禁用");
        } else if (user.getCredentialsExpired()) {
            throw new CredentialsExpiredException("凭证已过期");
        } else if (user.getLocked() && new Date().before(user.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(new Date().toInstant(),user.getLockTime().toInstant()));
        } else {
            //获取请求IP地址
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = NetworkUtil.getIpAddress(request);

            //更新用户信息
            user.setLocked(Boolean.FALSE);
            user.setLockLimit(NumberUtils.INTEGER_ZERO);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(ipAddress);
            userMapper.update(user);

            //获取用户上下文
            UserContext userContext = loginService.getUserContext(user, currrole);

            //登录成功事件
            LoginSuccessEvent.onSuccess(user);
            return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        }
    }

}
