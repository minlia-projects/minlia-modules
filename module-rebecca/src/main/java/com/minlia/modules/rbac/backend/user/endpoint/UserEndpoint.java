package com.minlia.modules.rbac.backend.user.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System User", description = "系统用户")
@RestController
@RequestMapping(value = ApiPrefix.API + "security/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody UserCreateRequestBody body) {
        return SuccessResponseBody.builder().payload(userService.create(body)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody UserUpdateRequestBody body) {
        return SuccessResponseBody.builder().payload(userService.update(body)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        userService.delete(id);
        return new SuccessResponseBody();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_UPDATE + "')")
    @ApiOperation(value = "授予角色", notes = "授予角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/grantRole", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody grantRole(@Valid @RequestBody UserGarenRequestBody requestBody) {
        userService.grantRole(requestBody);
        return SuccessResponseBody.builder().message("授权成功").build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_UPDATE + "')")
    @ApiOperation(value = "撤销角色", notes = "撤销角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/revokeRole", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody revokeRole(UserGarenRequestBody requestBody) {
        userService.revokeRole(requestBody);
        return SuccessResponseBody.builder().message("取消授权成功").build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserOperationService.USER_OPERATION_FREEZE + "')")
    @ApiOperation(value = "锁定用户", notes = "锁定用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/locked", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody locked(@RequestParam String guid) {
        return SuccessResponseBody.builder().payload(userService.locked(guid)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserOperationService.USER_OPERATION_UNFREEZE + "')")
    @ApiOperation(value = "禁用用户", notes = "禁用用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/disabled", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody disabled(@RequestParam String guid) {
        return SuccessResponseBody.builder().payload(userService.disabled(guid)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_READ + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(userQueryService.queryById(id)).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + UserService.ENTITY_READ + "')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody search(@PageableDefault Pageable pageable, @RequestBody UserQueryRequestBody body) {
        Page x = userQueryService.queryPage(body, pageable);
        return SuccessResponseBody.builder().payload(x).build();
    }

}
