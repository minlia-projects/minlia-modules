package com.minlia.module.rebecca.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.user.bean.SysPasswordByRawSysPasswordChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordResetTo;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.service.SysUserPasswordService;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.code.SecurityCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author will
 * @date 6/19/17
 */
@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.API + "user/password")
public class SysForgetPasswordEndpoint {

    private final BCryptPasswordEncoder encoder;
    private final SysUserService sysUserService;
    private final SysUserPasswordService sysUserPasswordService;

    public SysForgetPasswordEndpoint(SysUserService sysUserService, BCryptPasswordEncoder encoder, SysUserPasswordService sysUserPasswordService) {
        this.sysUserService = sysUserService;
        this.encoder = encoder;
        this.sysUserPasswordService = sysUserPasswordService;
    }

    @AuditLog(value = "change password", type = AuditOperationTypeEnum.UPDATE)
    @ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "forget", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response resetPassword(@Valid @RequestBody SysPasswordResetTo to) {
        return Response.success(sysUserPasswordService.forget(to));
    }

    @AuditLog(value = "update password by account and raw password", type = AuditOperationTypeEnum.UPDATE)
    @ApiOperation(value = "根据原密码修改", notes = "修改密码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "raw", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response changePassword(@Valid @RequestBody SysPasswordByRawSysPasswordChangeTo to) {
        SysUserEntity entity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery()
                .eq(SysUserEntity::getUsername, to.getUsername())
                .eq(SysUserEntity::getCellphone, to.getUsername())
                .eq(SysUserEntity::getEmail, to.getUsername()));
        ApiAssert.notNull(entity, SysUserCode.Message.NOT_EXISTS);
        ApiAssert.state(entity.getDisFlag(), SysUserCode.Message.ALREADY_DISABLED);
        ApiAssert.state(!(entity.getLockFlag() && LocalDateTime.now().isBefore(entity.getLockTime())), SecurityCode.Exception.AJAX_LOCKED, entity.getLockTime());

        if (!encoder.matches(to.getRawPassword(), entity.getPassword())) {
            //密码错误 锁定次数+1
            entity.setLockLimit(entity.getLockLimit() + NumberUtils.INTEGER_ONE);
            //如果超过3次 直接锁定
            if (entity.getLockLimit() > 2) {
                entity.setLockFlag(true);
                //1、按错误次数累加时间   2、错误3次锁定一天
                entity.setLockTime(LocalDateTime.now().plusMinutes((int) Math.pow(entity.getLockLimit() - 3, 3)));
            }
            sysUserService.update(entity, SysUserUpdateTypeEnum.PASSWORD_ERROR);

            Response response = Response.failure(SecurityCode.Exception.AJAX_BAD_CREDENTIALS, null, entity.getLockLimit().toString());
            response.setStatus(Response.STATUS_FAILURE);
            return response;
        }
        sysUserPasswordService.change(entity, to.getNewPassword());
        return Response.success();
    }

}
