package com.minlia.module.rebecca.navigation.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.navigation.bean.SysNavigationQro;
import com.minlia.module.rebecca.navigation.bean.SysNavigationSro;
import com.minlia.module.rebecca.navigation.constant.SysNavigationCode;
import com.minlia.module.rebecca.navigation.entity.SysNavigationEntity;
import com.minlia.module.rebecca.navigation.service.SysNavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.minlia.module.rebecca.navigation.service.impl.SysNavigationServiceImpl.EXISTS_ROLE_TEMPLATE;

/**
 * <p>
 * 导航 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Api(tags = "System Navigation", description = "菜单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "navigation")
public class SysNavigationController {

    private final SysNavigationService sysNavigationService;

    public SysNavigationController(SysNavigationService sysNavigationService) {
        this.sysNavigationService = sysNavigationService;
    }

    @AuditLog(value = "create a navigation", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response createByParent(@Valid @RequestBody SysNavigationSro sro) {
        return Response.success(sysNavigationService.create(sro));
    }

    @AuditLog(value = "update a navigation", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping
    public Response update(@Valid @RequestBody SysNavigationSro sro) {
        return Response.success(sysNavigationService.update(sro));
    }

    @AuditLog(value = "delete a navigation by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysNavigationService.delete(id));
    }

    @AuditLog(value = "toggle navigation status", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.DISPLAY + "')")
    @ApiOperation(value = "显示/隐藏")
    @GetMapping(value = "hide/{id}")
    public Response hide(@PathVariable Long id) {
        return Response.success(sysNavigationService.hide(id));
    }

    @AuditLog(value = "query navigation by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response getById(@RequestParam Long id) {
        return Response.success(sysNavigationService.getById(id));
    }

    @AuditLog(value = "query navigation by parent id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "父ID查询")
    @GetMapping(value = "parentid/{id}")
    public Response getByParentId(@PathVariable Long id) {
        LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>()
                .lambda().eq(SysNavigationEntity::getParentId, id).orderByAsc(SysNavigationEntity::getSort);
        List<SysNavigationEntity> list = sysNavigationService.list(queryWrapper);
        sysNavigationService.setChildren(list, null, null, null);
        return Response.success(list);
    }

    @AuditLog(value = "query navigation by role id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "角色ID查询")
    @GetMapping(value = "roleid/{id}")
    public Response getByRoleId(@PathVariable Long id) {
        String existsRole = String.format(EXISTS_ROLE_TEMPLATE, id);
        LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>()
                .lambda().eq(SysNavigationEntity::getParentId, 0).exists(existsRole).orderByAsc(SysNavigationEntity::getSort);
        List<SysNavigationEntity> list = sysNavigationService.list(queryWrapper);
        sysNavigationService.setChildren(list, null, null, existsRole);
        return Response.success(list);
    }

    @AuditLog(value = "query navigations by query request body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody SysNavigationQro qro) {
        LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysNavigationEntity.class)).last(qro.getOrderBy());
        List<SysNavigationEntity> list = sysNavigationService.list(queryWrapper);
        sysNavigationService.setChildren(list, null, null, null);
        return Response.success(list);
    }

    @AuditLog(value = "query navigations by query request body as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@RequestBody SysNavigationQro qro) {
        LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysNavigationEntity.class)).last(qro.getOrderBy());
        Page<SysNavigationEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        sysNavigationService.page(page, queryWrapper);
        sysNavigationService.setChildren(page.getRecords(), null, null, null);
        return Response.success(page);
    }

    @AuditLog(value = "my navigations", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(sysNavigationService.getMe());
    }

    @AuditLog(value = "get navigations tree", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysNavigationCode.SEARCH + "')")
    @ApiOperation(value = "树")
    @GetMapping(value = "tree")
    public Response getTree() {
        return Response.success(sysNavigationService.getTree());
    }

}
