package com.minlia.modules.rebecca.authentication;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.riskcontrol.constant.RiskCode;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.event.RiskIpScopeEvent;
import com.minlia.module.riskcontrol.service.DimensionService;
import com.minlia.module.riskcontrol.service.KieService;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.enumeration.UserStatusEnum;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.event.LoginSuccessEvent;
import com.minlia.modules.rebecca.mapper.UserMapper;
import com.minlia.modules.rebecca.risk.event.RiskLoginEvent;
import com.minlia.modules.rebecca.risk.event.RiskLoginFailureEvent;
import com.minlia.modules.rebecca.service.LoginService;
import com.minlia.modules.rebecca.service.UserService;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.authentication.service.AuthenticationService;
import com.minlia.modules.security.enumeration.LoginMethodEnum;
import com.minlia.modules.security.exception.AjaxBadCredentialsException;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.model.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
@Slf4j
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
    @Autowired
    private DimensionService dimensionService;

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
                user = userMapper.queryOne(UserQO.builder().username(loginCredentials.getUsername()).roleCode(currrole).build());
                break;
            case CELLPHONE:
                ApiAssert.hasLength(loginCredentials.getCellphone(), UserCode.Message.CELLPHONE_NOT_NULL);
                user = userMapper.queryOne(UserQO.builder().cellphone(loginCredentials.getCellphone()).roleCode(currrole).build());
                break;
            case EMAIL:
                ApiAssert.hasLength(loginCredentials.getEmail(), UserCode.Message.EMAIL_NOT_NULL);
                user = userMapper.queryOne(UserQO.builder().email(loginCredentials.getEmail()).roleCode(currrole).build());
                break;
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not exists:");
        }

        //只正对BORROWER角色
        if ((StringUtils.isNotBlank(currrole) && currrole.equals("BORROWER")) || user.getDefaultRole().equals("BORROWER")) {
            //登陆IP
            RiskLoginEvent riskLoginEvent = new RiskLoginEvent();
            riskLoginEvent.setScene("NUM_DIFF_IP_LOGIN_15MINS");
            riskLoginEvent.setSceneValue(loginCredentials.getAccount());
            riskLoginEvent.setUsername(loginCredentials.getAccount());
            KieService.execute(riskLoginEvent);
            ApiAssert.state(!riskLoginEvent.getLevel().equals(RiskLevelEnum.DANGER), RiskCode.Message.NUM_DIFF_IP_LOGIN_MINS.code(), RiskCode.Message.NUM_DIFF_IP_LOGIN_MINS.i18nKey());

            //登陆失败
            RiskLoginFailureEvent riskLoginFailureEvent = new RiskLoginFailureEvent();
            riskLoginFailureEvent.setSceneValue(loginCredentials.getAccount());
            KieService.execute(riskLoginFailureEvent);
            ApiAssert.state(!riskLoginEvent.getLevel().equals(RiskLevelEnum.DANGER), RiskCode.Message.NUM_SAME_IP_LOGIN_FAILURE_MINS);
        }

        if (StringUtils.isNotBlank(password)) {
            //凭证有效期
            if (null != user.getCredentialsEffectiveDate() && user.getCredentialsEffectiveDate().isBefore(LocalDateTime.now())) {
                throw new CredentialsExpiredException("登陆凭证已过期");
            }

            if (!encoder.matches(password, user.getPassword())) {
                //缓存登陆失败记录 TODO
//                dimensionService.distinctCountWithRedisAndConfig(new RiskLoginFailureEvent(), new String[]{RiskLoginFailureEvent.IP}, RiskLoginFailureEvent.TIME);

                //密码错误 锁定次数+1
                user.setLockLimit(user.getLockLimit() + NumberUtils.INTEGER_ONE);
                //如果超过3次 直接锁定
                if (user.getLockLimit() > 2) {
                    user.setLocked(true);
                    //1、按错误次数累加时间   2、错误3次锁定一天
                    user.setLockTime(LocalDateTime.now().plusMinutes((int) Math.pow(user.getLockLimit() - 3, 3)));
                }
                userService.update(user, UserUpdateTypeEcnum.PASSWORD_ERROR);
                throw new AjaxBadCredentialsException("Password error", user.getLockLimit());
            }
        }
        if (StringUtils.isNotBlank(captcha)) {
            Code code;
            if (LoginMethodEnum.CELLPHONE.equals(loginCredentials.getMethod())) {
                code = captchaService.validityByCellphone(user.getCellphone(), captcha, false);
            } else {
                code = captchaService.validityByEmail(user.getEmail(), captcha, false);
            }
            if (!CaptchaCode.Message.VERIFY_SUCCESS.name().equals(code.code())) {
                throw new DefaultAuthenticationException(code);
            }
        }

        if (null != user.getAccountEffectiveDate() && user.getAccountEffectiveDate().isBefore(LocalDateTime.now())) {
            throw new AccountExpiredException("账号已过期");
        } else if (UserStatusEnum.INACTIVE.equals(user.getStatus())) {
            throw new DisabledException("账号已禁用");
        } else if (UserStatusEnum.TERMINATED.equals(user.getStatus())) {
            throw new DefaultAuthenticationException(UserCode.Message.ALREADY_TERMINATED);
        } else if (user.getLocked() && LocalDateTime.now().isBefore(user.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(LocalDateTime.now(), user.getLockTime()));
        } else {
            //清除缓存登陆失败记录 TODO
            dimensionService.cleanCountWithRedis(new RiskLoginFailureEvent(), new String[]{RiskLoginFailureEvent.IP}, RiskLoginFailureEvent.TIME);

            //更新用户信息
            user.setLocked(Boolean.FALSE);
            user.setLockLimit(NumberUtils.INTEGER_ZERO);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(RequestIpUtils.getClientIP());
            userMapper.update(user);

            //获取用户上下文
            UserContext userContext = loginService.getUserContext(user, currrole);

            //登录成功事件
            LoginSuccessEvent.onSuccess(user);
            return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        }
    }

}