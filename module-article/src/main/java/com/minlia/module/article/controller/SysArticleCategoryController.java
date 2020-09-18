package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticleCategoryEntity;
import com.minlia.module.article.bean.ArticleCategoryQro;
import com.minlia.module.article.bean.ArticleCategorySro;
import com.minlia.module.article.service.SysArticleCategoryService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 文章类目 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Category", description = "文章-类目")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/category")
public class SysArticleCategoryController {

    private final SysArticleCategoryService sysArticleCategoryService;

    public SysArticleCategoryController(SysArticleCategoryService sysArticleCategoryService) {
        this.sysArticleCategoryService = sysArticleCategoryService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody ArticleCategorySro sro) {
        return Response.success(sysArticleCategoryService.saveOrUpdate(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody ArticleCategorySro sro) {
        return Response.success(sysArticleCategoryService.saveOrUpdate(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.UPDATE + "')")
    @ApiOperation(value = "禁用/启用")
    @PutMapping(value = "disable/{id}")
    public Response disable(@PathVariable Long id) {
        return Response.success(sysArticleCategoryService.disable(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysArticleCategoryService.delete(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysArticleCategoryService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody ArticleCategoryQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleCategoryEntity.class)).last(qro.getOrderBy());
        return Response.success(sysArticleCategoryService.count(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody ArticleCategoryQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleCategoryEntity.class)).last(qro.getOrderBy());
        List<SysArticleCategoryEntity> list = sysArticleCategoryService.list(queryWrapper);
        setChildren(list);
        return Response.success(list);
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ArticleCategoryQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleCategoryEntity.class)).last(qro.getOrderBy());
        Page<SysArticleCategoryEntity> page = sysArticleCategoryService.page(new Page(qro.getPageNumber(), qro.getPageSize()), queryWrapper);
        setChildren(page.getRecords());
        return Response.success(page);
    }

    private List<SysArticleCategoryEntity> setChildren(List<SysArticleCategoryEntity> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(articleCategory -> setChildren(articleCategory));
        }
        return list;
    }

    private void setChildren(SysArticleCategoryEntity entity) {
        if (null != entity) {
            //获取子项
            LambdaQueryWrapper queryWrapper = Wrappers.<SysArticleCategoryEntity>lambdaQuery().eq(SysArticleCategoryEntity::getParentId, entity.getId());
            List<SysArticleCategoryEntity> children = sysArticleCategoryService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(children)) {
                entity.setChildren(children);
                children.stream().forEach(category -> setChildren(category));
            }
        }
    }

}

