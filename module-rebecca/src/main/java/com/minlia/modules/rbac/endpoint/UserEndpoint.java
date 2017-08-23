package com.minlia.modules.rbac.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.endpoint.body.UserGarentRoleRequestBody;
import com.minlia.modules.rbac.service.UserQueryService;
import com.minlia.modules.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/users")
@Api(tags = "系统用户管理", description = "系统用户管理")
@Slf4j
public class UserEndpoint   {

    @Autowired
    UserService userWriteOnlyService;

    @Autowired
    UserQueryService userReadOnlyService;




    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_CREATE+"')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody User body) {
        return SuccessResponseBody.builder().payload(userWriteOnlyService.save(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_UPDATE+"')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody User body) {
        return SuccessResponseBody.builder().payload(userWriteOnlyService.update(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_DELETE+"')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        userWriteOnlyService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_READ+"')")
    @ApiOperation(value = "获取一个指定ID的", notes = "获取一个指定ID的用户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return SuccessResponseBody.builder().payload(userReadOnlyService.findOne(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_UPDATE+"')")
    @ApiOperation(value = "授予角色", notes = "授予角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/grantRole", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody grantRole(@Valid @RequestBody UserGarentRoleRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(userWriteOnlyService.grantRole(requestBody)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+UserService.ENTITY_UPDATE+"')")
    @ApiOperation(value = "撤销角色", notes = "撤销角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/revokeRole", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody revokeRole(UserGarentRoleRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(userWriteOnlyService.revokeRole(requestBody)).build();
    }

}
