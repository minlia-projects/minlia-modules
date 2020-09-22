package com.minlia.module.advertisement.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.SysAdvertisementsQro;
import com.minlia.module.advertisement.bean.SysAdvertisementsSro;
import com.minlia.module.advertisement.constant.SysAdvertisementConstants;
import com.minlia.module.advertisement.entity.SysAdvertisementsEntity;
import com.minlia.module.advertisement.service.SysAdvertisementsService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 广告集 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-22
 */
@Api(tags = "System Advertisements", description = "广告集")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "advertisements")
public class SysAdvertisementsController {

    private final SysAdvertisementsService sysAdvertisementsService;

    public SysAdvertisementsController(SysAdvertisementsService sysAdvertisementsService) {
        this.sysAdvertisementsService = sysAdvertisementsService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysAdvertisementsSro sro) {
        return Response.success(sysAdvertisementsService.save(DozerUtils.map(sro, SysAdvertisementsEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysAdvertisementsSro sro) {
        return Response.success(sysAdvertisementsService.updateById(DozerUtils.map(sro, SysAdvertisementsEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysAdvertisementsService.removeById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysAdvertisementsService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody SysAdvertisementsQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementsEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementsService.count(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysAdvertisementsQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementsEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementsService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysAdvertisementsQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementsEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementsService.page(new Page(qro.getPageNumber(), qro.getPageSize()), queryWrapper));
    }

}

