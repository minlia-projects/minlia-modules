package com.minlia.modules.rbac.v1.backend.grant;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.WithIdItemBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/19/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/grant")
@Api(tags = "安全", description = "安全")
@Slf4j
public class PermissionGrantEndpoint {


  @Autowired
  PermissionGrantService permissionGrantService;

  @PreAuthorize(value = "hasAnyAuthority('" + PermissionGrantService.ENTITY_READ + "')")
  @ApiOperation(value = "查找所有权限点", notes = "查找所有权限点", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "findAllPermissions", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody findAllPermissions(@PageableDefault Pageable pageable) {
    Page<Permission> entities = permissionGrantService.findAllPermissions(pageable);
    return SuccessResponseBody.builder().payload(entities).build();
  }

  @PreAuthorize(value = "hasAnyAuthority('" + PermissionGrantService.ENTITY_READ + "')")
  @ApiOperation(value = "查找指定角色已拥有的权限点", notes = "查找指定角色已拥有的权限点", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "findPermissionsByRoleId/{roleId}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody findPermissionsByRoleId(@PathVariable Long roleId,
      @PageableDefault Pageable pageable) {
    Page<Permission> entities = permissionGrantService.findPermissionsByRoleId(roleId, pageable);
    return SuccessResponseBody.builder().payload(entities).build();
  }

  @PreAuthorize(value = "hasAnyAuthority('" + PermissionGrantService.ENTITY_READ + "')")
  @ApiOperation(value = "查找指定用户已拥有的角色", notes = "查找指定用户已拥有的角色", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "findRoleByUserId/{userId}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody findRoleByUserId(@PathVariable Long userId,
      @PageableDefault Pageable pageable) {
    Page<Role> entities = permissionGrantService.findRoleByUserId(userId, pageable);
    return SuccessResponseBody.builder().payload(entities).build();
  }

  @PreAuthorize(value = "hasAnyAuthority('" + PermissionGrantService.ENTITY_GRANT + "')")
  @ApiOperation(value = "给指定角色授权权限点", notes = "给指定角色授权权限点并返回新授权的权限点", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "grantPermissionByRoleId/{roleId}", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody grantPermissionsByRoleId(
      @Valid @RequestBody @ApiParam(value = "授权请求体") WithIdItemBody body,
      @ApiParam(value = "角色ID") @PathVariable Long roleId) {
    Set<Permission> entities = permissionGrantService.grantPermissionsByRoleId(roleId, body);
    return SuccessResponseBody.builder().payload(entities).build();
  }


}
