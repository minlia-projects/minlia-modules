package com.minlia.modules.rbac.v1.backend.role;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.service.RoleReadOnlyService;
import com.minlia.modules.rbac.service.RoleWriteOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = ApiPrefix.V1 + "security/roles")
@Api(tags = "Security", description = "安全")
@Slf4j
public class RoleEndpoint {

  @Autowired
  RoleWriteOnlyService roleWriteOnlyService;

  @Autowired
  RoleReadOnlyService roleReadOnlyService;

  /**
   * 创建
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + RoleConstant.ENTITY_CREATE + "')")
  @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody create(@Valid @RequestBody Role body) {
    return SuccessResponseBody.builder().payload(roleWriteOnlyService.save(body)).build();
  }

  /**
   * PUT /api/v1/bible/update 根据当前实体id修改
   * 更新
   * 使用JSR301在线验证
   */
  @PreAuthorize(value = "hasAnyAuthority('" + RoleConstant.ENTITY_UPDATE + "')")
  @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody update(@Valid @RequestBody Role body) {
    return SuccessResponseBody.builder().payload(roleWriteOnlyService.update(body)).build();
  }

  /**
   * 删除
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + RoleConstant.ENTITY_DELETE + "')")
  @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  //consumes = {MediaType.APPLICATION_JSON_VALUE},
  public StatefulBody delete(@PathVariable Long id) {
    roleWriteOnlyService.delete(id);
    return SuccessResponseBody.builder().build();
  }

  /**
   * 获取一个指定ID的
   * 使用JSR301在线验证
   * <p>
   * 使用非RequestBody类型请求时不能出现consumes
   */
  @PreAuthorize(value = "hasAnyAuthority('" + RoleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "获取一个指定ID的", notes = "获取一个指定ID的", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody findOne(@PathVariable Long id) {
    return SuccessResponseBody.builder().payload(roleReadOnlyService.findOne(id)).build();
  }


}
