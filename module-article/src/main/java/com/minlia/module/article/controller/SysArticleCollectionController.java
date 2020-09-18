package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticleCollectionEntity;
import com.minlia.module.article.service.SysArticleCollectionService;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章收藏 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Collection", description = "文章-收藏")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/collection")
public class SysArticleCollectionController {

    private final SysArticleCollectionService sysArticleCollectionService;

    public SysArticleCollectionController(SysArticleCollectionService sysArticleCollectionService) {
        this.sysArticleCollectionService = sysArticleCollectionService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.CREATE + "')")
    @ApiOperation(value = "收藏")
    @PostMapping(value = "{articleId}")
    public Response collection(@PathVariable Long articleId) {
        return Response.success(sysArticleCollectionService.collection(articleId));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.READ + "')")
    @ApiOperation(value = "是否收藏")
    @GetMapping(value = "collected/{articleId}")
    public Response isCollected(@PathVariable Long articleId) {
        return Response.success(sysArticleCollectionService.isCollected(articleId));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @RequestMapping(value = "count")
    public Response count() {
        return Response.success(sysArticleCollectionService.count(Wrappers.<SysArticleCollectionEntity>lambdaQuery()
                .eq(SysArticleCollectionEntity::getOperator, SecurityContextHolder.getCurrentGuid())));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "page")
    public Response page(@Valid @RequestBody BaseQueryEntity qro) {
        return Response.success(sysArticleCollectionService.myPage(qro.getPageNumber(), qro.getPageSize()));
    }

}