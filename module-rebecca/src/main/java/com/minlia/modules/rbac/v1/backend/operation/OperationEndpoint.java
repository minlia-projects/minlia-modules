package com.minlia.modules.rbac.v1.backend.operation;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.WithIdBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 9/8/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "user/operation")
@Api(tags = "System User", description = "系统用户管理")
@Slf4j
public class OperationEndpoint {

  @Autowired
  UserOperationService userOperationService;

  @PreAuthorize(value = "hasAnyAuthority('" + UserOperationService.USER_OPERATION_FREEZE + "')")
  @ApiOperation(value = "锁定用户", notes = "锁定用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "/freeze", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody freeze(@RequestBody WithIdBody body) {
    Boolean frozen = userOperationService.freeze(body.getId());
    return SuccessResponseBody.builder().payload(frozen).build();
  }

  @PreAuthorize(value = "hasAnyAuthority('" + UserOperationService.USER_OPERATION_UNFREEZE + "')")
  @ApiOperation(value = "解除锁定用户", notes = "解除锁定用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "/unfreeze", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody unfreeze(@RequestBody WithIdBody body) {
    Boolean frozen = userOperationService.unfreeze(body.getId());
    return SuccessResponseBody.builder().payload(frozen).build();
  }

}
