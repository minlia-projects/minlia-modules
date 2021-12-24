package com.minlia.module.member.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author garen
 */
@Api(tags = "System Member Open", description = "会员")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "member")
public class SysMemberOpenController {

    private final SysUserRegisterService sysUserRegisterService;

    public SysMemberOpenController(SysUserRegisterService sysUserRegisterService) {
        this.sysUserRegisterService = sysUserRegisterService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "注册")
    @PostMapping(value = "register")
    public Response register(@Valid @RequestBody UserRegisterRo registerRo) {
        registerRo.setRoleCode(SysMemberConstants.DEFAULT_ROLE);
        return sysUserRegisterService.register(registerRo);
    }

}