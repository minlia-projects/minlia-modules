
package com.minlia.modules.rebecca.menu.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.modules.rebecca.constant.RebeccaSecurityConstant;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import com.minlia.modules.rebecca.menu.bean.MenuGrantRO;
import com.minlia.modules.rebecca.menu.bean.MenuQRO;
import com.minlia.modules.rebecca.menu.entity.Menu;
import com.minlia.modules.rebecca.menu.service.MenuService;
import com.minlia.modules.rebecca.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2017/6/17.
 */
@Api(tags = "System Menu", description = "菜单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "menu")
public class MenuEndpoint {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @AuditLog(value = "create menu")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_CREATE + "')")
    @ApiOperation(value = "创建")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response createByParent(@Valid @RequestBody Menu menu) {
        return Response.success(menuService.insertSelective(menu));
    }

    @AuditLog(value = "update menu")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_UPDATE + "')")
    @ApiOperation(value = "更新")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response update(@Valid @RequestBody Menu menu) {
        return Response.success(menuService.updateByPrimaryKeySelective(menu));
    }

    @AuditLog(value = "toggle menu status")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DELETE + "')")
    @ApiOperation(value = "禁用/启用")
    @RequestMapping(value = "disable/{id}", method = RequestMethod.PUT)
    public Response disable(@PathVariable Long id) {
        return Response.success(menuService.disable(id));
    }

    @AuditLog(value = "toggle menu display status")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DISPLAY + "')")
    @ApiOperation(value = "显示/隐藏")
    @RequestMapping(value = "display", method = RequestMethod.PUT)
    public Response display(@RequestParam Long id) {
        return Response.success(menuService.display(id));
    }

    @AuditLog(value = "delete menu by id")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_DELETE + "')")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable Long id) {
        return Response.success(menuService.delete(id));
    }

    @AuditLog(value = "grant menu to role")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_GRANT + "')")
    @ApiOperation(value = "授权")
    @RequestMapping(value = "grant", method = RequestMethod.PUT)
    public Response grant(@Valid @RequestBody MenuGrantRO grantRO) {
        return Response.success(menuService.grant(grantRO));
    }

    @AuditLog(value = "get my menus")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        String currentRole = SecurityContextHolder.getUserContext().getCurrrole();
        Long roleId = roleService.queryByCode(currentRole).getId();
        return Response.success(menuService.selectByAll(MenuQRO.builder().roleId(roleId).isOneLevel(true).hideFlag(false).disFlag(false).build()));
    }

    @AuditLog(value = "query menu by id")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(menuService.selectByPrimaryKey(id));
    }

    @AuditLog(value = "query menus by query request body as list")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response queryList(@RequestBody MenuQRO qro) {
        return Response.success(menuService.selectByAll(qro));
    }

    @AuditLog(value = "query menus by query request body as paginated result")
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaSecurityConstant.NAVIGATION_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response queryPage(@PageableDefault(direction = Sort.Direction.ASC,sort = "id")Pageable pageable,@RequestBody MenuQRO qro) {
        PageInfo<Menu> pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(()-> menuService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}
