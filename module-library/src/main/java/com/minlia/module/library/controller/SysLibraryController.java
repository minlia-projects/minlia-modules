package com.minlia.module.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.library.bean.SysLibraryQro;
import com.minlia.module.library.bean.SysLibrarySro;
import com.minlia.module.library.constant.SysLibraryConstant;
import com.minlia.module.library.entity.SysLibraryEntity;
import com.minlia.module.library.service.SysLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * <p>
 * 文库 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Api(tags = "System Library", description = "文库")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/library")
public class SysLibraryController {

    private final SysLibraryService sysLibraryService;

    public SysLibraryController(SysLibraryService sysLibraryService) {
        this.sysLibraryService = sysLibraryService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.UPLOAD + "')")
    @ApiOperation(value = "OCR")
    @PostMapping(value = "ocr")
    public Response upload(MultipartFile file) {
        return Response.success(sysLibraryService.ocr(file));
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysLibrarySro sro) {
        sro.setId(null);
        return Response.success(sysLibraryService.save(DozerUtils.map(sro, SysLibraryEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysLibrarySro sro) {
        return Response.success(sysLibraryService.updateById(DozerUtils.map(sro, SysLibraryEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.UPDATE + "')")
    @ApiOperation(value = "禁用")
    @PutMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(sysLibraryService.disable(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysLibraryService.removeById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysLibraryService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysLibraryQro qro) {
        SysLibraryEntity entity = DozerUtils.map(qro, SysLibraryEntity.class);
        LambdaQueryWrapper<SysLibraryEntity> queryWrapper = new QueryWrapper<SysLibraryEntity>().lambda()
                .select(SysLibraryEntity::getId, SysLibraryEntity::getName, SysLibraryEntity::getUrl, SysLibraryEntity::getSummary)
                .setEntity(entity);
        if (StringUtils.isNotBlank(qro.getOrderBy())) {
            queryWrapper.last(qro.getOrderBy());
        } else {
            queryWrapper.orderByDesc(SysLibraryEntity::getCreateDate);
        }
        return Response.success(sysLibraryService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysLibraryConstant.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysLibraryQro qro) {
        SysLibraryEntity entity = DozerUtils.map(qro, SysLibraryEntity.class);
        LambdaQueryWrapper<SysLibraryEntity> queryWrapper = new QueryWrapper<SysLibraryEntity>().lambda()
                .select(SysLibraryEntity::getId, SysLibraryEntity::getName, SysLibraryEntity::getUrl, SysLibraryEntity::getSummary)
                .setEntity(entity);
        if (StringUtils.isNotBlank(qro.getOrderBy())) {
            queryWrapper.last(qro.getOrderBy());
        } else {
            queryWrapper.orderByDesc(SysLibraryEntity::getCreateDate);
        }
        Page<SysLibraryEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysLibraryService.page(page, queryWrapper));
    }

}