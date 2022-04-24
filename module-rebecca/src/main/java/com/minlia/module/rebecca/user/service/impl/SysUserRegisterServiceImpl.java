package com.minlia.module.rebecca.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.rebecca.risk.event.RiskRegistrationEvent;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.bean.UserAvailablitityTo;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.module.riskcontrol.service.RiskKieService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author garen
 */
@Service
public class SysUserRegisterServiceImpl implements SysUserRegisterService {

    private final SysUserService sysUserService;

    private final CaptchaService captchaService;

    public SysUserRegisterServiceImpl(SysUserService sysUserService, CaptchaService captchaService) {
        this.sysUserService = sysUserService;
        this.captchaService = captchaService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<SysUserEntity> register(UserRegisterRo registerRo) {
        switch (registerRo.getType()) {
            case USERNAME:
                ApiAssert.state(false, SysUserCode.Message.UNSUPPORTED_USERNAME_REGISTERED);
                break;
            case CELLPHONE:
                return registerByCellphone(registerRo);
            case EMAIL:
                return registerByEmail(registerRo);
            default:
                break;
        }
        return Response.failure();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<SysUserEntity> registerByCellphone(UserRegisterRo ro) {
        Response response = captchaService.validity(ro.getAreaCode() + ro.getCellphone(), ro.getVcode());
        if (response.isSuccess()) {
            //注册风控 TODO
            RiskKieService.execute(new RiskRegistrationEvent(ro.getCellphone()));

            SysUserEntity entity = sysUserService.create(SysUserCro.builder()
                    .areaCode(ro.getAreaCode())
                    .cellphone(ro.getCellphone())
                    .password(ro.getPassword())
                    .roles(Sets.newHashSet())
                    .inviteCode(ro.getInviteCode())
                    .nickname(ro.getNickname())
                    .defaultRole(ro.getRoleCode())
                    .build());
            return Response.success(entity);
        } else {
            return response;
        }
    }

    private Response<SysUserEntity> registerByEmail(UserRegisterRo ro) {
        Response response = captchaService.validity(ro.getEmail(), ro.getVcode());
        if (response.isSuccess()) {
            SysUserEntity entity = sysUserService.create(SysUserCro.builder()
                    .email(ro.getEmail())
                    .password(ro.getPassword())
                    .defaultRole(ro.getRoleCode())
                    .roles(Sets.newHashSet())
                    .inviteCode(ro.getInviteCode())
                    .nickname(ro.getNickname())
                    .defaultRole(ro.getRoleCode())
                    .build());
            return Response.success(entity);
        } else {
            return response;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response availablitity(UserAvailablitityTo body) {
        if (StringUtils.isNotBlank(body.getUsername()) && sysUserService.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, body.getUsername())) > 0) {
            return Response.failure(SysUserCode.Message.USERNAME_ALREADY_EXISTS);
        }
        if (StringUtils.isNotBlank(body.getCellphone()) && sysUserService.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getCellphone, body.getAreaCode() + body.getCellphone())) > 0) {
            return Response.failure(SysUserCode.Message.CELLPHONE_ALREADY_EXISTS);
        }
        if (StringUtils.isNotBlank(body.getUsername()) && sysUserService.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, body.getEmail())) > 0) {
            return Response.failure(SysUserCode.Message.EMAIL_ALREADY_EXISTS);
        }
        return Response.success("Available");
    }

}