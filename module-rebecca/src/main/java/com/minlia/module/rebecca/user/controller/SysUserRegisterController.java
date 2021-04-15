package com.minlia.module.rebecca.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.bean.UserAvailablitityTo;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import com.minlia.module.rebecca.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author garen
 * @date 2017/7/19
 */
@Api(tags = "System Registration", description = "用户-注册")
@RestController
@RequestMapping(value = ApiPrefix.API + "user/registration")
public class SysUserRegisterController {

    private final SysUserService sysUserService;

    private final SysUserRegisterService userRegistrationService;

    public SysUserRegisterController(SysUserService sysUserService, SysUserRegisterService userRegistrationService) {
        this.sysUserService = sysUserService;
        this.userRegistrationService = userRegistrationService;
    }

    @AuditLog(value = "user register", type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "注册")
    @PostMapping(value = "")
    public Response registration(@Valid @RequestBody UserRegisterRo to) {
        return userRegistrationService.register(to);
    }

    @AuditLog(value = "user availability check", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "用户名验证")
    @PostMapping(value = "availabilitity")
    public Response availability(@Valid @RequestBody UserAvailablitityTo body) {
        return userRegistrationService.availablitity(body);
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "邀请码是否存在")
    @PostMapping(value = "verifyInviteCode")
    public Response verifyInviteCode(@RequestParam String inviteCode) {
        if (sysUserService.count(Wrappers.<SysUserEntity>lambdaUpdate().eq(SysUserEntity::getInviteCode, inviteCode).eq(SysUserEntity::getDisFlag, false)) > 0) {
            return Response.success(true);
        } else {
            return Response.failure(SysUserCode.Message.INVITE_CODE_NOT_EXISTS, false);
        }
    }

}