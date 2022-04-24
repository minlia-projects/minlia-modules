package com.minlia.module.rebecca.authentication;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.rebecca.risk.event.RiskLoginFailureEvent;
import com.minlia.module.rebecca.risk.event.RiskRegistrationEvent;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.event.SysLoginSuccessEvent;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.module.riskcontrol.service.RiskDimensionService;
import com.minlia.module.riskcontrol.service.RiskKieService;
import com.minlia.modules.security.authentication.cellphone.CellphoneLoginCredentials;
import com.minlia.modules.security.authentication.service.AuthenticationService;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.model.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


/**
 * 短信验证码认证 服务类
 *
 * @author garen
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CellphoneVerificationCodeAuthenticationService implements AuthenticationService {

    private final LoginService loginService;
    private final SysUserService sysUserService;
    private final CaptchaService captchaService;
    private final RiskDimensionService riskDimensionService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Authentication authentication(Authentication authentication) {
        log.info("手机号认证开始-----------------------------------------");
        CellphoneLoginCredentials loginCredentials = (CellphoneLoginCredentials) authentication.getPrincipal();

        log.info("手机号认证校验验证码-----------------------------------------");
        Response response = captchaService.validity(loginCredentials.getAreaCode() + loginCredentials.getCellphone(), loginCredentials.getVcode());
        if (!response.isSuccess()) {
            throw new DefaultAuthenticationException(response.getI18nCode());
        }
        SysUserEntity userEntity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getCellphone, loginCredentials.getCellphone()).eq(SysUserEntity::getAreaCode, loginCredentials.getAreaCode()));
        if (Objects.isNull(userEntity)) {
            //注册风控 TODO
            RiskKieService.execute(new RiskRegistrationEvent(loginCredentials.getCellphone()));
            log.info("手机号码不存在，创建账号------------------------------------");
            userEntity = sysUserService.create(SysUserCro.builder().areaCode(loginCredentials.getAreaCode()).cellphone(loginCredentials.getCellphone()).inviteCode(loginCredentials.getInviteCode()).roles(Sets.newHashSet()).defaultRole("ROLE_MEMBER").build());
        } else {
            log.info("手机号码存在，更新信息------------------------------------");
            update(userEntity);
        }

        //登录成功事件
        SysLoginSuccessEvent.publish(userEntity);

        //清除缓存登陆失败记录 TODO
        riskDimensionService.cleanCountWithRedis(new RiskLoginFailureEvent(loginCredentials.getCellphone()), new String[]{RiskLoginFailureEvent.SCENE_VALUE}, RiskLoginFailureEvent.TIME);
        return getUsernamePasswordAuthenticationToken(userEntity);
    }

    /**
     * 前置校验
     *
     * @param userEntity
     */
    private void preConditions(SysUserEntity userEntity) {
        if (null != userEntity.getAccountEffectiveDate() && userEntity.getAccountEffectiveDate().isBefore(LocalDateTime.now())) {
            throw new AccountExpiredException("账号已过期");
        } else if (userEntity.getDisFlag()) {
            throw new DisabledException("账号已禁用");
        } else if (userEntity.getLockFlag() && LocalDateTime.now().isBefore(userEntity.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(LocalDateTime.now(), userEntity.getLockTime()));
        } else if (SysUserStatusEnum.TERMINATED.equals(userEntity.getStatus())) {
            throw new DefaultAuthenticationException(SysUserCode.Message.ALREADY_TERMINATED);
        }
    }

    /**
     * 更新用户信息
     *
     * @param userEntity
     */
    private void update(SysUserEntity userEntity) {
        userEntity.setLockFlag(Boolean.FALSE);
        userEntity.setLockLimit(NumberUtils.INTEGER_ZERO);
        userEntity.setLastLoginTime(LocalDateTime.now());
        userEntity.setLastLoginIp(RequestIpUtils.getClientIP());
        sysUserService.update(userEntity, SysUserUpdateTypeEnum.SYSTEM_UPDATE);
    }

    /**
     * 获取用户TOKEN
     *
     * @param userEntity
     * @return
     */
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(SysUserEntity userEntity) {
        UserContext userContext = loginService.getUserContext(userEntity, userEntity.getDefaultRole());
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

}