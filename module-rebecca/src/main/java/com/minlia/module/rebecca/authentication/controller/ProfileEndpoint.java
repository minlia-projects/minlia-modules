package com.minlia.module.rebecca.authentication.controller;

import com.minlia.cloud.body.Response;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.navigation.service.SysNavigationService;
import com.minlia.modules.security.model.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author garen
 */
@RestController
@Api(tags = "System Security", description = "系统安全")
public class ProfileEndpoint {

    private final SysNavigationService sysNavigationService;

    public ProfileEndpoint(SysNavigationService sysNavigationService) {
        this.sysNavigationService = sysNavigationService;
    }

    @AuditLog(value = "get my authorities as list", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "获取我的权限列表", notes = "获取我的权限列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/v1/me", method = RequestMethod.GET)
    public Response get() {
        UserContext context = SecurityContextHolder.getUserContext();
        context.setNavigations(sysNavigationService.getMe());
        return Response.success(context);
    }

}