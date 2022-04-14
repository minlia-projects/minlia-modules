package com.minlia.module.rebecca.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.SysPasswordByCaptchaChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordByRawPasswordChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordResetTo;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.service.SysUserPasswordService;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * @author garen
 */
@Service
public class SysUserPasswordServiceImpl implements SysUserPasswordService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean forget(SysPasswordResetTo to) {
        SysUserEntity entity = null;
        //校验凭证是否有效
        switch (to.getType()) {
            case CELLPHONE:
                entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery()
                        .eq(SysUserEntity::getCellphone, to.getCellphone())
                        .eq(SysUserEntity::getAreaCode, to.getAreaCode()));
                ApiAssert.notNull(entity, SysUserCode.Message.NOT_EXISTS);
                Response response = captchaService.validity(to.getAreaCode() + to.getCellphone(), to.getVcode());
                ApiAssert.state(response.isSuccess(), response.getCode(), response.getMessage());
                break;
            case EMAIL:
                entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, to.getEmail()));
                ApiAssert.notNull(entity, SysUserCode.Message.NOT_EXISTS);
                Response responseEmail = captchaService.validity(to.getEmail(), to.getVcode());
                ApiAssert.state(responseEmail.isSuccess(), responseEmail.getCode(), responseEmail.getMessage());
                break;
            default:
        }
        return change(entity, to.getNewPassword());
    }

    @Override
    public boolean change(SysPasswordByCaptchaChangeTo to) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        Response response = captchaService.validity(entity.getAreaCode() + entity.getCellphone(), to.getVcode());
        ApiAssert.state(response.isSuccess(), response.getCode(), response.getMessage());
        return change(entity, to.getNewPassword());
    }

    @Override
    public boolean change(SysPasswordByRawPasswordChangeTo to) {
        SysUserEntity entity = SecurityContextHolder.getCurrentUser();
        Boolean bool = bCryptPasswordEncoder.matches(to.getRawPassword(), entity.getPassword());
        ApiAssert.state(bool, SysUserCode.Message.RAW_PASSWORD_ERROR);
        return change(entity, to.getNewPassword());
    }

    @Override
    public boolean change(SysUserEntity entity, String newPassword) {
        ApiAssert.state(!bCryptPasswordEncoder.matches(newPassword, entity.getPassword()), SysUserCode.Message.NEW_PASSWORD_EQUALS_OLD);

        //设置新密码
        entity.setPassword(bCryptPasswordEncoder.encode(newPassword));
        entity.setCredentialsEffectiveDate(LocalDateTime.now().plusYears(1));
        entity.setLockFlag(Boolean.FALSE);
        entity.setLockLimit(0);
        entity.setLockTime(LocalDateTime.now());
        sysUserService.update(entity, SysUserUpdateTypeEnum.CHANGE_PASSWORD);
        //注销token
        //TokenCacheUtils.kill(entity.getId());
        return true;
    }

}
