package com.minlia.module.rebecca.authentication.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.authentication.credential.LoginEmailCredential;
import com.minlia.modules.security.authentication.credential.LoginMobileCredential;
import com.minlia.modules.security.authentication.credential.LoginUsernameCredential;
import com.minlia.modules.security.constant.SecurityConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "System Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth/login")
public class LoginEndpoint {

    @AuditLog(value = "login", type = AuditOperationTypeEnum.LOGIN)
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "")
    public Response login(@Valid @RequestBody LoginCredentials credential) {
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.LOGIN)
    @ApiOperation(value = "手机号码登录")
    @PostMapping(value = "mobile")
    public Response loginByMobile(@Valid @RequestBody LoginMobileCredential credential) {
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.LOGIN)
    @ApiOperation(value = "邮箱登录")
    @PostMapping(value = "email")
    public Response loginByEmail(@Valid @RequestBody LoginEmailCredential credential) {
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.LOGIN)
    @ApiOperation(value = "用户名登录")
    @PostMapping(value = "username")
    public Response loginByUsername(@Valid @RequestBody LoginUsernameCredential credential) {
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.LOGIN)
    @ApiOperation(value = "check", notes = "检查")
    @GetMapping(value = "check")
    public void session(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(SecurityConstant.SID, request.getSession().getId());
    }

}