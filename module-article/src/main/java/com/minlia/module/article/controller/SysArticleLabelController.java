package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticleLabelEntity;
import com.minlia.module.article.bean.ArticleLabelSro;
import com.minlia.module.article.bean.ArticleLabelQro;
import com.minlia.module.article.service.SysArticleLabelService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章标签 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Label", description = "文章-标签")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/label")
public class SysArticleLabelController {

    private final SysArticleLabelService sysArticleLabelService;

    public SysArticleLabelController(SysArticleLabelService sysArticleLabelService) {
        this.sysArticleLabelService = sysArticleLabelService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody ArticleLabelSro sro) {
        return Response.success(sysArticleLabelService.save(DozerUtils.map(sro, SysArticleLabelEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody ArticleLabelSro sro) {
        return Response.success(sysArticleLabelService.updateById(DozerUtils.map(sro, SysArticleLabelEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysArticleLabelService.delete(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysArticleLabelService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody ArticleLabelQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysArticleLabelEntity.class))
                .last(qro.getOrderBy());
        return Response.success(sysArticleLabelService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ArticleLabelQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysArticleLabelEntity.class))
                .last(qro.getOrderBy());
        Page page = new Page(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysArticleLabelService.page(page, queryWrapper));
    }

}

