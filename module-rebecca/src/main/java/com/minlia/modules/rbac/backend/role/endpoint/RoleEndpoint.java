package com.minlia.modules.rbac.backend.role.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.backend.common.constant.RebeccaSecurityConstant;
import com.minlia.modules.rbac.backend.role.body.RoleCreateRequestBody;
import com.minlia.modules.rbac.backend.role.body.RoleUpdateRequestBody;
import com.minlia.modules.rbac.backend.role.service.RoleService;
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
@Api(tags = "System Role", description = "角色")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/role")
public class RoleEndpoint {

    @Autowired
    private RoleService roleService;

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_CREATE +"')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody RoleCreateRequestBody body) {
        return Response.success(SystemCode.Message.CREATE_SUCCESS, roleService.create(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_UPDATE +"')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody RoleUpdateRequestBody body) {
        return Response.success(SystemCode.Message.CREATE_SUCCESS, roleService.update(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_DELETE +"')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{code}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String code) {
        roleService.delete(code);
        return Response.success(SystemCode.Message.DELETE_SUCCESS);
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_GRANT +"')")
    @ApiOperation(value = "授权", notes = "授权", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "grant/{code}", consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response grantPermissions(@PathVariable String code, @RequestBody List<Long> permissions) {
        roleService.grantPermission(code,permissions);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
    @ApiOperation(value = "根据ID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryById/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryById(@PathVariable Long id) {
        return Response.success(roleService.queryById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
    @ApiOperation(value = "根据CODE查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByCode/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByCode(@PathVariable String code) {
        return Response.success(roleService.queryByCode(code));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
    @ApiOperation(value = "根据GUID查询", notes = "ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "queryByGuid/{guid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByGuid(@PathVariable String guid) {
        return Response.success(roleService.queryByGuid(guid));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RebeccaSecurityConstant.ROLE_SEARCH +"')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "queryPage", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPage(@PageableDefault Pageable pageable) {
        return Response.success(roleService.queryPage(pageable));
    }

}
