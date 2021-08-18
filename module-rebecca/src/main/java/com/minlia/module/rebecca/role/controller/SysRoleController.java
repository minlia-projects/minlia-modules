package com.minlia.module.rebecca.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.role.bean.SysRoleGrantRo;
import com.minlia.module.rebecca.role.bean.SysRoleQro;
import com.minlia.module.rebecca.role.bean.SysRoleSro;
import com.minlia.module.rebecca.role.constant.SysRoleCode;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Api(tags = "System SysRole", description = "角色")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @AuditLog(value = "create system role", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody SysRoleSro sro) {
        return Response.success(SystemCode.Message.CREATE_SUCCESS, sysRoleService.save(DozerUtils.map(sro, SysRoleEntity.class)));
    }

    @AuditLog(value = "update system role", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody SysRoleSro sro) {
        return Response.success(SystemCode.Message.UPDATE_SUCCESS, sysRoleService.updateById(DozerUtils.map(sro, SysRoleEntity.class)));
    }

    @AuditLog(value = "delete system role by code", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(SystemCode.Message.DELETE_SUCCESS, sysRoleService.removeRoleById(id));
    }

    @AuditLog(value = "query system role by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response getById(@PathVariable Long id) {
        return Response.success(sysRoleService.getById(id));
    }

    @AuditLog(value = "query system role by code", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
    @ApiOperation(value = "CODE查询")
    @GetMapping(value = "code/{code}")
    public Response getByCode(@PathVariable String code) {
        return Response.success(sysRoleService.getOne(new QueryWrapper<SysRoleEntity>().lambda().eq(SysRoleEntity::getCode, code)));
    }

    @AuditLog(value = "query system role by user id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
    @ApiOperation(value = "UserId查询")
    @GetMapping(value = "user/{userid}")
    public Response getByUid(@PathVariable Long userid) {
        return Response.success(sysRoleService.getRolesByUserId(userid));
    }

    @AuditLog(value = "query sys roles as page result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysRoleQro qro) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysRoleEntity.class));
        return Response.success(sysRoleService.list(queryWrapper));
    }

    @AuditLog(value = "query system roles as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysRoleQro qro) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>()
                .lambda().setEntity(DozerUtils.map(qro, SysRoleEntity.class));
        return Response.success(sysRoleService.page(qro.getPage(), queryWrapper));
    }

//    @AuditLog(value = "query system role tree", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.SEARCH + "')")
//    @ApiOperation(value = "树形菜单")
//    @GetMapping(value = "tree")
//    public Response getTree() {
//        return Response.success(sysRoleService.getTree());
//    }

    @AuditLog(value = "grant navigation to system role", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.GRANT + "')")
    @ApiOperation(value = "授权-导航")
    @PostMapping(value = "grant/navigation")
    public Response grantNavigation(@Valid @RequestBody SysRoleGrantRo grantRo) {
        sysRoleService.grantNavigation(grantRo.getRoleId(), grantRo.getIds());
        return Response.success();
    }

    @AuditLog(value = "grant permissions to system role", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysRoleCode.GRANT + "')")
    @ApiOperation(value = "授权-权限点")
    @PostMapping(value = "grant/permission")
    public Response grantPermission(@Valid @RequestBody SysRoleGrantRo grantRo) {
        sysRoleService.grantPermission(grantRo.getRoleId(), grantRo.getIds());
        return Response.success();
    }

}