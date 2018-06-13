package com.minlia.modules.rbac.backend.user.endpoint;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.common.constant.RebeccaSecurityConstant;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "System User", description = "系统用户")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody UserCreateRequestBody body) {
        return SuccessResponseBody.builder().payload(userService.create(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody UserUpdateRequestBody body) {
        return SuccessResponseBody.builder().payload(userService.update(body)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{guid}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable String guid) {
        userService.delete(guid);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "锁定用户", notes = "锁定用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "locked", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody locked(@RequestParam String guid) {
        return SuccessResponseBody.builder().payload(userService.locked(guid)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "禁用用户", notes = "禁用用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "disabled", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody disabled(@RequestParam String guid) {
        return SuccessResponseBody.builder().payload(userService.disabled(guid)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_GRANT + "')")
    @ApiOperation(value = "授予角色", notes = "授予角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "grant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody grant(@Valid @RequestBody UserGarenRequestBody requestBody) {
        userService.grant(requestBody);
        return SuccessResponseBody.builder().message("授权成功").build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "me", notes = "me", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "me", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody me() {
        return SuccessResponseBody.builder().payload(SecurityContextHolder.getCurrentUser()).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody findOne(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(userQueryService.queryById(id)).build();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody search(@PageableDefault Pageable pageable, @RequestBody UserQueryRequestBody body) {
        RowBounds rowBounds2 = new RowBounds(1,10);
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<User> users =  userQueryService.queryList(body);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return SuccessResponseBody.builder().payload(pageInfo).build();
    }

}
