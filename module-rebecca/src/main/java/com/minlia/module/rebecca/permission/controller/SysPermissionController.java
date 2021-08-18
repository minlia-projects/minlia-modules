package com.minlia.module.rebecca.permission.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.permission.bean.SysPermissionQro;
import com.minlia.module.rebecca.permission.constant.SysPermissionCode;
import com.minlia.module.rebecca.permission.entity.SysPermissionEntity;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 权限点 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Api(tags = "System Permission", description = "权限点")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/permission")
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    public SysPermissionController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @AuditLog(value = "update authority", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysPermissionCode.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PostMapping
    public Response update(@Valid @RequestBody SysPermissionEntity entity) {
        return Response.success(sysPermissionService.updateById(entity));
    }

    @AuditLog(value = "clear authority", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysPermissionCode.CLEAR + "')")
    @ApiOperation(value = "清理")
    @DeleteMapping(value = "clear")
    public Response clear() {
        return Response.success(sysPermissionService.clear());
    }

    @AuditLog(value = "query authorities as tree", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysPermissionCode.SEARCH + "')")
    @ApiOperation(value = "查询树")
    @GetMapping(value = "tree")
    public Response tree() {
        return Response.success(sysPermissionService.tree());
    }

    @AuditLog(value = "query authorities as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysPermissionCode.SEARCH + "')")
    @ApiOperation(value = " 集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysPermissionQro qro) {
        //TODO 根据userId、roleId查询
        LambdaQueryWrapper<SysPermissionEntity> queryWrapper = new QueryWrapper<SysPermissionEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysPermissionEntity.class))
                ;
        if (null != qro.getRoleId()) {
            List<Long> permissionIds = sysPermissionService.getIdsByRoleId(qro.getRoleId());
            if (CollectionUtils.isNotEmpty(permissionIds)) {
                queryWrapper.in(SysPermissionEntity::getId, permissionIds);
            } else {
                return Response.success();
            }
        }
        if (null != qro.getUserId()) {
            List<Long> permissionIds = sysPermissionService.getIdsByUserId(qro.getUserId());
            if (CollectionUtils.isNotEmpty(permissionIds)) {
                queryWrapper.in(SysPermissionEntity::getId, permissionIds);
            } else {
                return Response.success();
            }
        }
        return Response.success(sysPermissionService.list(queryWrapper));
    }

    @AuditLog(value = "query sysRoles as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysPermissionCode.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysPermissionQro qro) {
        //TODO 根据userId、roleId查询
        LambdaQueryWrapper<SysPermissionEntity> queryWrapper = new QueryWrapper<SysPermissionEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysPermissionEntity.class))
                ;
        if (null != qro.getRoleId()) {
            List<Long> permissionIds = sysPermissionService.getIdsByRoleId(qro.getRoleId());
            if (CollectionUtils.isNotEmpty(permissionIds)) {
                queryWrapper.in(SysPermissionEntity::getId, permissionIds);
            } else {
                return Response.success();
            }
        }
        if (null != qro.getUserId()) {
            List<Long> permissionIds = sysPermissionService.getIdsByUserId(qro.getUserId());
            if (CollectionUtils.isNotEmpty(permissionIds)) {
                queryWrapper.in(SysPermissionEntity::getId, permissionIds);
            } else {
                return Response.success();
            }
        }
        return Response.success(sysPermissionService.page(qro.getPage(), queryWrapper));
    }

}