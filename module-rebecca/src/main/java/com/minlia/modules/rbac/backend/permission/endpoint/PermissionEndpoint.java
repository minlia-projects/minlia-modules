
package com.minlia.modules.rbac.backend.permission.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.common.constant.RebeccaSecurityConstant;
import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateRequestBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
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

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_UPDATE +"')")
    @ApiOperation(value = "update", notes = "update", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody PermissionUpdateRequestBody body) {
        Permission permission = permissionService.update(body);
        return SuccessResponseBody.builder().payload(permission).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_UPDATE +"')")
    @ApiOperation(value = "clear", notes = "clear", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "clear", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody clear() {
        permissionService.clear();
        return SuccessResponseBody.builder().message("清除成功").build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_GRANT +"')")
    @ApiOperation(value = "tree", notes = "查询所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "tree", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody tree() {
        return SuccessResponseBody.builder().payload(permissionService.tree()).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "查询所有(集合)", notes = "查询所有", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryAll() {
        List<Permission> permissions = permissionService.queryAll();
        return SuccessResponseBody.builder().payload(permissions).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据用户查询(集合)", notes = "根据用户查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByGuid", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryListByGuid(@RequestParam String guid) {
        List<Permission> permissions = permissionService.queryListByGuid(guid);
        return SuccessResponseBody.builder().payload(permissions).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据角色查询(集合)", notes = "根据角色查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRole", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByRole(@RequestParam String code) {
        List<Permission> permissions = permissionService.queryListByRoleCodes(Lists.newArrayList(code));
        return SuccessResponseBody.builder().payload(permissions).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "根据角色查询(集合)", notes = "根据角色查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRoles", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryByRoles(@RequestParam List<String> codes) {
        List<Permission> permissions = permissionService.queryListByRoleCodes(codes);
        return SuccessResponseBody.builder().payload(permissions).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.PERMISSION_SEARCH +"')")
    @ApiOperation(value = "查询分页", notes = "查询分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryPage", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryPageByRole(@PageableDefault Pageable pageable) {
        PageHelper.startPage(new Integer(new Long(pageable.getOffset()).toString()), pageable.getPageSize());
        List<Permission> permissions =  permissionService.queryAll();
        PageInfo<Permission> pageInfo = new PageInfo<Permission>(permissions);

//        Page<Permission> entities = permissionService.queryPage(rowBounds);
        return SuccessResponseBody.builder().payload(pageInfo).build();
    }

}
