package com.minlia.module.rebecca.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.bean.SysUserGrantTO;
import com.minlia.module.rebecca.user.bean.SysUserQro;
import com.minlia.module.rebecca.user.bean.SysUserUro;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-24
 */
@Api(tags = "System User", description = "系统用户")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @AuditLog(value = "create user", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody SysUserCro cro) {
        return Response.success(sysUserService.create(cro));
    }

    @AuditLog(value = "update user", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody SysUserUro uro) {
        return Response.success(sysUserService.update(uro, SysUserUpdateTypeEnum.SYSTEM_UPDATE));
    }

    @AuditLog(value = "delete user by uid", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysUserService.delete(id));
    }

    @AuditLog(value = "lock user by uid", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "锁定用户")
    @PutMapping(value = "lock/{id}")
    public Response locked(@PathVariable Long id) {
        return Response.success(sysUserService.lock(id));
    }

    @AuditLog(value = "disable user by uid", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.UPDATE + "')")
    @ApiOperation(value = "禁用用户")
    @PutMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(sysUserService.disable(id));
    }

    @AuditLog(value = "grant roles to user", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.GRANT + "')")
    @ApiOperation(value = "授予角色")
    @PostMapping(value = "grant")
    public Response grant(@Valid @RequestBody SysUserGrantTO to) {
        return Response.success(sysUserService.grant(to.getUid(), to.getRoles()));
    }

    @AuditLog(value = "get my details", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.READ + "')")
    @ApiOperation(value = "me", notes = "me", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "me", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response me() {
        return Response.success(SecurityContextHolder.getUserContext());
    }

    @AuditLog(value = "get details by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response uid(@PathVariable Long id) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getId, id);
        return Response.success(sysUserService.getOne(queryWrapper));
    }

    @AuditLog(value = "query a user", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.SEARCH + "')")
    @ApiOperation(value = "单个查询")
    @PostMapping(value = "one")
    public Response one(@Valid @RequestBody SysUserQro qro) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysUserEntity.class))
                .last(qro.getOrderBy());
        return Response.success(sysUserService.getOne(queryWrapper));
    }

    @AuditLog(value = "query user as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysUserQro qro) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysUserEntity.class))
                .last(qro.getOrderBy());
        return Response.success(sysUserService.list(queryWrapper));
    }

    @AuditLog(value = "query user as paginated result", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysUserCode.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysUserQro qro) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().lambda()
                .setEntity(DozerUtils.map(qro, SysUserEntity.class))
                .last(qro.getOrderBy());
        Page<SysUserEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysUserService.page(page, queryWrapper));
    }

}