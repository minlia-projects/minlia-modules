package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.ArticleQro;
import com.minlia.module.article.bean.ArticleSro;
import com.minlia.module.article.bean.vo.ArticleVo;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticleEntity;
import com.minlia.module.article.service.SysArticleCategoryService;
import com.minlia.module.article.service.SysArticleService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article", description = "文章")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article")
public class SysArticleController {

    private final SysArticleService sysArticleService;
    private final SysArticleCategoryService sysArticleCategoryService;

    public SysArticleController(SysArticleService sysArticleService, SysArticleCategoryService sysArticleCategoryService) {
        this.sysArticleService = sysArticleService;
        this.sysArticleCategoryService = sysArticleCategoryService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody ArticleSro sro) {
        return Response.success(sysArticleService.saveOrUpdate(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody ArticleSro sro) {
        return Response.success(sysArticleService.saveOrUpdate(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(SystemCode.Message.DELETE_SUCCESS, sysArticleService.delete(id));
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + ArticleConstants.SEARCH + "')")
//    @ApiOperation(value = "ID查询")
//    @GetMapping(value = "{id}")
//    public Response id(@PathVariable Long id) {
//        return Response.success(sysArticleService.getById(id));
//    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody ArticleQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleEntity.class)).last(qro.getOrderBy());
        return Response.success(sysArticleService.count(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ArticleQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleEntity.class)).last(qro.getOrderBy());
        Page page = new Page(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysArticleService.page(page, queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "详情")
    @GetMapping(value = "details/{id}")
    public Response details(@PathVariable Long id) {
        ArticleVo vo = sysArticleService.details(id);
        if (null != vo) {
            //设置级联categoryIds
            vo.setCategoryIds(sysArticleCategoryService.selectCategoryIdsById(vo.getCategoryId()));
        }
        return Response.success(vo);
    }

//    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(value = "details/page")
//    public Response datailsPage(@Valid @RequestBody ArticleQro qro) {
//        return Response.success(sysArticleService.detailsPage(qro));
//    }

}