package com.minlia.modules.rebecca.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.to.UserCTO;
import com.minlia.modules.rebecca.bean.to.UserGrantTO;
import com.minlia.modules.rebecca.bean.to.UserUTO;
import com.minlia.modules.rebecca.constant.RebeccaSecurityConstant;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System User", description = "系统用户")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;

    @AuditLog(value = "create user")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody UserCTO cto) {
        return Response.success(userService.create(cto));
    }

    @AuditLog(value = "update user")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody UserUTO uto) {
        return Response.success(userService.update(uto, UserUpdateTypeEcnum.SYSTEM_UPDATE));
    }

    @AuditLog(value = "delete user by guid")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{guid}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String guid) {
        userService.delete(guid);
        return Response.success();
    }

    @AuditLog(value = "lock user by guid")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "锁定用户", notes = "锁定用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "locked/{guid}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response locked(@PathVariable String guid) {
        return Response.success(userService.locked(guid));
    }

    @AuditLog(value = "disable user by guid")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_UPDATE + "')")
    @ApiOperation(value = "禁用用户", notes = "禁用用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "disabled/{guid}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response disabled(@PathVariable String guid) {
        return Response.success(userService.disabled(guid));
    }

    @AuditLog(value = "grant roles to user")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_GRANT + "')")
    @ApiOperation(value = "授予角色", notes = "授予角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "grant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response grant(@Valid @RequestBody UserGrantTO to) {
        userService.grant(to.getGuid(),to.getRoles());
        return Response.success();
    }

    @AuditLog(value = "get my details")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_READ + "')")
    @ApiOperation(value = "me", notes = "me", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "me", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response me() {
        return Response.success(SecurityContextHolder.getUserContext());
    }

    @AuditLog(value = "get my details")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "GUID查询", notes = "GUID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{guid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response guid(@PathVariable String guid) {
        return Response.success(userQueryService.queryOne(UserQO.builder().guid(guid).build()));
    }

    @AuditLog(value = "query a user")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "one", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response one(@RequestBody UserQO body) {
        return Response.success(userQueryService.queryOne(body));
    }

    @AuditLog(value = "query user as list")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody UserQO body) {
        return Response.success(userQueryService.queryList(body));
    }

    @AuditLog(value = "query user as paginated result")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.USER_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response page(@PageableDefault Pageable pageable, @RequestBody UserQO body) {
        return Response.success(userQueryService.queryPage(body, pageable));
    }

}