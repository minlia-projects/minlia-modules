package com.minlia.module.rebecca.user.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.user.bean.SysPasswordByCaptchaChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordByRawPasswordChangeTo;
import com.minlia.module.rebecca.user.service.SysUserPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author garen
 */
@Api(tags = "System Password", description = "密码")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/password")
public class SysChangePasswordEndpoint {

    @Autowired
    private SysUserPasswordService userPasswordService;

    @AuditLog(value = "update password by raw password", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据原密码修改")
    @PostMapping(value = "raw")
    public Response changePasswordMode1(@Valid @RequestBody SysPasswordByRawPasswordChangeTo body) {
        return Response.success(userPasswordService.change(body));
    }

    @AuditLog(value = "update password by otp", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "根据验证码修改")
    @PostMapping(value = "vcode")
    public Response changePasswordMode2(@Valid @RequestBody SysPasswordByCaptchaChangeTo body) {
        return Response.success(userPasswordService.change(body));
    }

}
