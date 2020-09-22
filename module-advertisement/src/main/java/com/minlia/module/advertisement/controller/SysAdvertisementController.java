package com.minlia.module.advertisement.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.SysAdvertisementQro;
import com.minlia.module.advertisement.bean.SysAdvertisementSro;
import com.minlia.module.advertisement.constant.SysAdvertisementConstants;
import com.minlia.module.advertisement.entity.SysAdvertisementEntity;
import com.minlia.module.advertisement.service.SysAdvertisementService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 广告 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-22
 */
@Api(tags = "System Advertisement", description = "广告")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "ad")
public class SysAdvertisementController {

    private final SysAdvertisementService sysAdvertisementService;

    public SysAdvertisementController(SysAdvertisementService sysAdvertisementService) {
        this.sysAdvertisementService = sysAdvertisementService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysAdvertisementSro sro) {
        return Response.success(sysAdvertisementService.save(DozerUtils.map(sro, SysAdvertisementEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysAdvertisementSro sro) {
        return Response.success(sysAdvertisementService.updateById(DozerUtils.map(sro, SysAdvertisementEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysAdvertisementService.removeById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysAdvertisementService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody SysAdvertisementQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementService.count(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysAdvertisementQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysAdvertisementConstants.AUTHORIZE_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysAdvertisementQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysAdvertisementEntity.class)).last(qro.getOrderBy());
        return Response.success(sysAdvertisementService.page(new Page(qro.getPageNumber(), qro.getPageSize()), queryWrapper));
    }

}