package com.minlia.modules.rbac.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rbac.bean.domain.Role;
import com.minlia.modules.rbac.bean.qo.NavigationQO;
import com.minlia.modules.rbac.bean.vo.MyNavigationVO;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.rbac.service.NavigationService;
import com.minlia.modules.rbac.service.RoleService;
import com.minlia.modules.security.model.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "System Security", description = "系统安全")
public class ProfileEndpoint {

    @Autowired
    private RoleService roleService;

    @Autowired
    private NavigationService navigationService;

    @AuditLog(value = "get my authorities as list")
    @ApiOperation(value = "获取我的权限列表", notes = "获取我的权限列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/v1/me", method = RequestMethod.GET)
    public Response get() {
        UserContext context = SecurityContextHolder.getUserContext();
        Role role = roleService.queryByCode(context.getCurrrole());
        List<MyNavigationVO> navigations = navigationService.queryMyNavigationList(NavigationQO.builder().isOneLevel(true).display(true).roleId(role.getId()).build());
        context.setNavigations(navigations);
        return Response.success(context);
    }

}