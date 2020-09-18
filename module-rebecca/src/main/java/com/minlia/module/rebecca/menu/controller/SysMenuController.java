package com.minlia.module.rebecca.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.menu.bean.SysMenuQro;
import com.minlia.module.rebecca.menu.bean.SysMenuSro;
import com.minlia.module.rebecca.menu.constant.MenuCode;
import com.minlia.module.rebecca.menu.entity.SysMenuEntity;
import com.minlia.module.rebecca.menu.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Api(tags = "System Menu", description = "菜单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @AuditLog(value = "create menu", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response createByParent(@Valid @RequestBody SysMenuSro sro) {
        return Response.success(sysMenuService.create(sro));
    }

    @AuditLog(value = "update menu", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody SysMenuSro sro) {
        return Response.success(sysMenuService.update(sro));
    }

    @AuditLog(value = "toggle menu status", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.DELETE + "')")
    @ApiOperation(value = "禁用/启用")
    @PutMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(sysMenuService.disable(id));
    }

    @AuditLog(value = "toggle menu display status", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.DISPLAY + "')")
    @ApiOperation(value = "显示/隐藏")
    @PutMapping(value = "hide")
    public Response hide(@RequestParam Long id) {
        return Response.success(sysMenuService.hide(id));
    }

    @AuditLog(value = "delete menu by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysMenuService.removeById(id));
    }

    @AuditLog(value = "query menu by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(sysMenuService.getById(id));
    }

    @AuditLog(value = "query menus by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response queryList(@RequestBody SysMenuQro qro) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<SysMenuEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysMenuEntity.class)).last(qro.getOrderBy());
        return Response.success(sysMenuService.list(queryWrapper));
    }

    @AuditLog(value = "query menus by query request body as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + MenuCode.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response queryPage(@PageableDefault(direction = Sort.Direction.ASC, sort = "id") Pageable pageable, @RequestBody SysMenuQro qro) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<SysMenuEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysMenuEntity.class)).last(qro.getOrderBy());
        Page<SysMenuEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysMenuService.page(page, queryWrapper));
    }

    private void setChildren(List<SysMenuEntity> entities, Long roleId) {
//        if (CollectionUtils.isNotEmpty(menus)) {
//            for (Menu menu : menus) {
//                if (NavigationType.FOLDER.equals(menu.getType())) {
//                    List<Menu> chirdren = menuMapper.selectByAll(SysMenuQro.builder().parentId(menu.getId()).hideFlag(false).roleId(roleId).build());
//                    menu.setChildren(chirdren);
//                    bindChirdren(chirdren, roleId);
//                }
//            }
//        }
    }

}