
package com.minlia.modules.rbac.backend.permission.endpoint;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "安全-权限点", description = "权限点")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/permission")
public class PermissionEndpoint {

    @Autowired
    PermissionService permissionService;

//    @PreAuthorize(value = "hasAnyAuthority('"+PermissionService.ENTITY_UPDATE+"')")
    @ApiOperation(value = "update", notes = "update", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody PermissionUpdateBody body) {
        Permission permission = permissionService.update(body);
        return SuccessResponseBody.builder().payload(permission).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('"+PermissionService.ENTITY_READ+"')")
    @ApiOperation(value = "查询所有", notes = "分页查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryPage", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryPage(@PageableDefault Pageable pageable) {
        Page<Permission> entities = permissionService.queryPage(pageable);
        return SuccessResponseBody.builder().payload(entities).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('"+PermissionService.ENTITY_READ+"')")
    @ApiOperation(value = "根据角色查询", notes = "根据角色查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryPageByRole/{code}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryPageByRole(@PathVariable String code, @PageableDefault Pageable pageable) {
        Page<Permission> entities = permissionService.queryPageByRoleCodes(Lists.newArrayList(code),pageable);
        return SuccessResponseBody.builder().payload(entities).build();
    }

}
