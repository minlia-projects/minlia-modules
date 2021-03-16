package com.minlia.module.article.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.constant.SysArticleConstants;
import com.minlia.module.article.entity.SysArticlePraiseEntity;
import com.minlia.module.article.service.SysArticlePraiseService;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章-点赞 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Praise", description = "文章-点赞")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "article/praise")
public class SysArticlePraiseController {

    private final SysArticlePraiseService sysArticlePraiseService;

    public SysArticlePraiseController(SysArticlePraiseService sysArticlePraiseService) {
        this.sysArticlePraiseService = sysArticlePraiseService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.Authorize.Praise.CREATE + "')")
    @ApiOperation(value = "点赞")
    @PostMapping(value = "{articleId}")
    public Response praise(@PathVariable Long articleId) {
        return Response.success(sysArticlePraiseService.praise(articleId));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.Authorize.Praise.READ + "')")
    @ApiOperation(value = "是否点赞")
    @GetMapping(value = "praised/{articleId}")
    public Response isPraised(@PathVariable Long articleId) {
        return Response.success(sysArticlePraiseService.isPraised(articleId));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.Authorize.Praise.SEARCH + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody BaseQueryEntity qro) {
        return Response.success(sysArticlePraiseService.count(Wrappers.<SysArticlePraiseEntity>lambdaQuery()
                .eq(SysArticlePraiseEntity::getOperator, SecurityContextHolder.getUid())));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + SysArticleConstants.Authorize.Praise.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody BaseQueryEntity qro) {
        return Response.success(sysArticlePraiseService.page(qro.getPageNumber(), qro.getPageSize()));
    }

}