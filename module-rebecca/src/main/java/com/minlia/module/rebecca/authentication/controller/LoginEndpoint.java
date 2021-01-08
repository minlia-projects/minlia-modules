package com.minlia.module.rebecca.authentication.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.constant.SecurityConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author will
 * @date 7/21/17
 * This is just a fake control for springfox-swagger2 ro generate api-docs
 */
@Api(tags = "System Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth/login")
public class LoginEndpoint {

    @AuditLog(value = "login", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "")
    public Response login(@Valid @RequestBody LoginCredentials credential) {
        return Response.success();
    }

    @ApiOperation(value = "check", notes = "检查")
    @RequestMapping(value = "check", method = RequestMethod.GET)
    public void session(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(SecurityConstant.SID, request.getSession().getId());
    }

}