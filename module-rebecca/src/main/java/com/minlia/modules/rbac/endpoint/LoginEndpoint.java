package com.minlia.modules.rbac.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 7/21/17.
 * This is just a fake control for springfox-swagger2 ro generate api-docs
 */
@Api(tags = "System Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth/login")
public class LoginEndpoint {


    @AuditLog(value = "login")
    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public Response login(@Valid @RequestBody LoginCredentials credential) {
        return Response.success();
    }

}
