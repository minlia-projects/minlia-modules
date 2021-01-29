package com.minlia.module.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.SysArticleCommentQro;
import com.minlia.module.article.entity.SysArticleCommentEntity;
import com.minlia.module.article.service.SysArticleCommentService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author garen
 */
@Api(tags = "System Article Comment Open", description = "文章-评论")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article/comment")
public class SysArticleCommentOpenController {

    private final SysArticleCommentService sysArticleCommentService;

    public SysArticleCommentOpenController(SysArticleCommentService sysArticleCommentService) {
        this.sysArticleCommentService = sysArticleCommentService;
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysArticleCommentQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysArticleCommentEntity.class))
                ;
        Page page = new Page(qro.getPageNumber(), qro.getPageSize());
        return Response.success(sysArticleCommentService.page(page, queryWrapper));
    }

}