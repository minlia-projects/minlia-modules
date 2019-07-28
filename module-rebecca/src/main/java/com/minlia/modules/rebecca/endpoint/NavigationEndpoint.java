
package com.minlia.modules.rebecca.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.modules.rebecca.bean.qo.NavigationQO;
import com.minlia.modules.rebecca.bean.to.NavigationCTO;
import com.minlia.modules.rebecca.bean.to.NavigationGrantTO;
import com.minlia.modules.rebecca.bean.to.NavigationUTO;
import com.minlia.modules.rebecca.constant.RebeccaSecurityConstant;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import com.minlia.modules.rebecca.service.NavigationService;
import com.minlia.modules.rebecca.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2017/6/17.
 */
@Api(tags = "System Navigation", description = "菜单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "navigation")
public class NavigationEndpoint {

    @Autowired
    private RoleService roleService;

    @Autowired
    private NavigationService navigationService;

    @AuditLog(value = "create a navigation", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response createByParent(@Valid @RequestBody NavigationCTO body) {
        return navigationService.create(body);
    }

    @AuditLog(value = "update a navigation", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody NavigationUTO body) {
        return Response.success(navigationService.update(body));
    }

    @AuditLog(value = "delete a navigation by id", type = OperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        navigationService.delete(id);
        return Response.success();
    }

    @AuditLog(value = "grant navigation to role", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_GRANT + "')")
    @ApiOperation(value = "授权", notes = "给角色分配菜单", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "grant", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response grant(@Valid @RequestBody NavigationGrantTO body) {
        navigationService.grant(body);
        return Response.success();
    }

    @AuditLog(value = "toggle navigation status", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DISPLAY + "')")
    @ApiOperation(value = "显示/隐藏", notes = "显示", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "display", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response display(@RequestParam Long id) {
        return Response.success(navigationService.display(id));
    }


    @AuditLog(value = "my navigations", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "我的", notes = "我的", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "me", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response me() {
        String currentRole = SecurityContextHolder.getUserContext().getCurrrole();
        Long roleId = roleService.queryByCode(currentRole).getId();
        return Response.success(navigationService.queryList(NavigationQO.builder().roleId(roleId).display(true).build()));
    }

    @AuditLog(value = "query navigation by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryById", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryById(@RequestParam Long id) {
        return Response.success(navigationService.queryById(id));
    }

    @AuditLog(value = "query navigation by parent id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据父ID查询", notes = "根据父ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByParentId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByParentId(@RequestParam Long id) {
        return Response.success(navigationService.queryList(NavigationQO.builder().parentId(id).build()));
    }

    @AuditLog(value = "query navigation by role id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "根据角色ID查询", notes = "根据角色ID查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "queryByRoleId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByRoleId(@RequestParam Long id) {
        return Response.success(navigationService.queryMyNavigationList(NavigationQO.builder().isOneLevel(true).roleId(id).build()));
    }

    @AuditLog(value = "query navigations by query request body as list", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryList(@RequestBody NavigationQO qro) {
        return Response.success(navigationService.queryList(qro));
    }

    @AuditLog(value = "query navigations by query request body as paginated result", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "查询分页", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPage(@PageableDefault(direction = Sort.Direction.ASC, sort = "id") Pageable pageable, @RequestBody NavigationQO qro) {
        return Response.success(navigationService.queryPage(qro, pageable));
    }

}
