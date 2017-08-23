package com.minlia.modules.rbac.v1.backend.role;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.service.RoleReadOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/roles/search")
@Api(tags = "System Security", description = "系统安全")
@Slf4j
public class RoleReadOnlyEndpoint {

  @Autowired
  RoleReadOnlyService roleReadOnlyService;

  //    @DataScope(value = {DataScopes.PUBLIC, DataScopes.OWNER})
  @PreAuthorize(value = "hasAnyAuthority('" + RoleConstant.ENTITY_READ + "')")
  @ApiOperation(value = "根据条件查询所有的角色", notes = "根据条件查询所有的角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public StatefulBody findAll(@PageableDefault Pageable pageable) {
    Page page = roleReadOnlyService.findAll(pageable);
    return SuccessResponseBody.builder().payload(page).build();
  }

}