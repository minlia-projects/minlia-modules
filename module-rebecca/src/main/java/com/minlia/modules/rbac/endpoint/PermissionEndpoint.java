
package com.minlia.modules.rbac.endpoint;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rbac.constant.RebeccaSecurityConstant;
import com.minlia.modules.rbac.bean.to.PermissionUTO;
import com.minlia.modules.rbac.bean.domain.Permission;
import com.minlia.modules.rbac.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Permission", description = "权限点")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/permission")
public class PermissionEndpoint {

    @Autowired
    private PermissionService permissionService;

    @AuditLog(value = "update authority")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_UPDATE +"')")
    @ApiOperation(value = "update", notes = "update", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody PermissionUTO body) {
        Permission permission = permissionService.update(body);
        return Response.success(permission);
    }

    @AuditLog(value = "clear authority")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_CLEAR +"')")
    @ApiOperation(value = "clear", notes = "clear", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "clear", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response clear() {
        permissionService.clear();
        return Response.success();
    }

    @AuditLog(value = "query authorities as tree")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "tree", notes = "查询所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "tree", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response tree() {
        return Response.success(permissionService.tree());
    }

    @AuditLog(value = "query authorities as list")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "查询所有(集合)", notes = "查询所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryAll() {
        List<Permission> permissions = permissionService.queryAll();
        return Response.success(permissions);
    }

    @AuditLog(value = "query authorities by guid as list")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据用户查询(集合)", notes = "根据用户查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByGuid", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryListByGuid(@RequestParam String guid) {
        List<Permission> permissions = permissionService.queryListByGuid(guid);
        return Response.success(permissions);
    }

    @AuditLog(value = "query authorities by role code as list")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据角色查询(集合)", notes = "根据角色查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRole", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByRole(@RequestParam String code) {
        List<Permission> permissions = permissionService.queryListByRoleCodes(Lists.newArrayList(code));
        return Response.success(permissions);
    }

    @AuditLog(value = "query authorities by role codes as list")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据角色查询(集合)", notes = "根据角色查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRoles", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByRoles(@RequestParam List<String> codes) {
        List<Permission> permissions = permissionService.queryListByRoleCodes(codes);
        return Response.success(permissions);
    }

    @AuditLog(value = "query authorities as paginated result")
    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "查询分页", notes = "查询分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryPage", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPageByRole(@PageableDefault Pageable pageable) {
        return Response.success(permissionService.queryPage(pageable));
    }

}
