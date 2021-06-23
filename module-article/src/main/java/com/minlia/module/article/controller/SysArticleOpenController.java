package com.minlia.module.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.ArticleQro;
import com.minlia.module.article.bean.vo.ArticleVo;
import com.minlia.module.article.entity.SysArticleCategoryEntity;
import com.minlia.module.article.entity.SysArticleEntity;
import com.minlia.module.article.service.SysArticleCategoryService;
import com.minlia.module.article.service.SysArticleService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Api(tags = "System Article Open", description = "文章-公开")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article")
public class SysArticleOpenController {

    private final SysArticleService sysArticleService;
    private final SysArticleCategoryService sysArticleCategoryService;

    public SysArticleOpenController(SysArticleService sysArticleService, SysArticleCategoryService sysArticleCategoryService) {
        this.sysArticleService = sysArticleService;
        this.sysArticleCategoryService = sysArticleCategoryService;
    }

    @ApiOperation(value = "详情")
    @GetMapping(value = "details/{id}")
    public Response findByNumber(@PathVariable Long id) {
        ArticleVo vo = sysArticleService.details(id);
        ApiAssert.notNull(vo, SystemCode.Message.DATA_NOT_EXISTS);
        //每次查询阅读数加1
        sysArticleService.plusReadCount(id, 1);
        return Response.success(vo);
    }

    @ApiOperation(value = "详情")
    @GetMapping(value = "details/code/{code}")
    public Response findByNumber(@PathVariable String code) {
        ArticleVo vo = sysArticleService.details(code);
        ApiAssert.notNull(vo, SystemCode.Message.DATA_NOT_EXISTS);
        //每次查询阅读数加1
        sysArticleService.plusReadCount(vo.getId(), 1);
        return Response.success(vo);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ArticleQro qro) {
        SysArticleCategoryEntity sysArticleCategoryEntity = sysArticleCategoryService.getOne(Wrappers.<SysArticleCategoryEntity>lambdaQuery().select(SysArticleCategoryEntity::getId).eq(SysArticleCategoryEntity::getCode, qro.getCategoryCode()));
        if (Objects.nonNull(sysArticleCategoryEntity)) {
            qro.setCategoryId(sysArticleCategoryEntity.getId());
        }
        LambdaQueryWrapper queryWrapper = Wrappers.<SysArticleEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleEntity.class));
        return Response.success(sysArticleService.page(qro.getPageNumber(), queryWrapper));
    }

//    @ApiOperation(value = "分页查询")
//    @PostMapping(value = "details/page")
//    public Response detailsPage(@Valid @RequestBody ArticleQro qro) {
//        qro.setDisFlag(false);
//        qro.setDraftFlag(false);
//        return Response.success(sysArticleService.detailsPage(qro));
//    }

}