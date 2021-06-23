package com.minlia.module.rebecca.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.rebecca.constant.RebeccaConstant;
import com.minlia.module.rebecca.entity.SysDataPermissionEntity;
import com.minlia.module.rebecca.service.SysDataPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 数据权限 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-03-03
 */
@Api(tags = "System Data Permission", description = "数据权限")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "data/permission")
@RequiredArgsConstructor
public class SysDataPermissionController {

    private final SysDataPermissionService sysDataPermissionService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.CREATE + "')")
    @ApiOperation(value = "创建所有")
    @PostMapping(value = "all")
    public Response createAllSelect(@RequestParam String className) {
        return Response.success(sysDataPermissionService.createAllSelect(className));
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysDataPermissionEntity entity) {
        return Response.success(sysDataPermissionService.create(entity));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysDataPermissionEntity entity) {
        return Response.success(sysDataPermissionService.update(entity));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response update(@PathVariable Long id) {
        return Response.success(sysDataPermissionService.delete(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @PathVariable SysDataPermissionEntity entity) {
        return Response.success(sysDataPermissionService.list(Wrappers.<SysDataPermissionEntity>lambdaQuery().setEntity(entity)));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RebeccaConstant.Authorize.DataPermission.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @PathVariable BaseQueryEntity entity) {
        return Response.success(sysDataPermissionService.page(entity.getPageNumber()));
    }

}