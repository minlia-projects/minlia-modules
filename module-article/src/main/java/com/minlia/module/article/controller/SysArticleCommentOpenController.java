package com.minlia.module.article.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.bean.SysArticleCommentQro;
import com.minlia.module.article.service.SysArticleCommentService;
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
        return Response.success(sysArticleCommentService.detailsPage(qro));
    }

}