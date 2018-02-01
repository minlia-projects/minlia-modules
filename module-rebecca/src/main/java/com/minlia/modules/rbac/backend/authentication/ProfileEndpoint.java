package com.minlia.modules.rbac.backend.authentication;

import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.security.model.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "System Security", description = "系统安全")
public class ProfileEndpoint {

    @ApiOperation(value = "获取我的权限列表", notes = "获取我的权限列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/v1/me", method = RequestMethod.GET)
    public UserContext get() {
        return  SecurityContextHolder.getUserContext();
    }

}
