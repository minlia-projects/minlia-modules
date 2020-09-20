package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.ArticleCommentCro;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticleCommentEntity;
import com.minlia.module.article.service.SysArticleCommentService;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章评论 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Comment", description = "文章-评论")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/comment")
public class SysArticleCommentController {

    private final SysArticleCommentService sysArticleCommentService;

    public SysArticleCommentController(SysArticleCommentService sysArticleCommentService) {
        this.sysArticleCommentService = sysArticleCommentService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "{articleId}")
    public Response create(@Valid @RequestBody ArticleCommentCro cro) {
        return Response.success(sysArticleCommentService.create(cro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.READ + "')")
    @ApiOperation(value = "是否评论")
    @GetMapping(value = "commented/{articleId}")
    public Response isCommented(@PathVariable Long articleId) {
        return Response.success(sysArticleCommentService.isCommented(articleId));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody BaseQueryEntity qro) {
        return Response.success(sysArticleCommentService.count(Wrappers.<SysArticleCommentEntity>lambdaQuery()
                .eq(SysArticleCommentEntity::getOperator, SecurityContextHolder.getUid())));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody BaseQueryEntity qro) {
        return Response.success(sysArticleCommentService.myPage(qro.getPageNumber(), qro.getPageSize()));
    }

}