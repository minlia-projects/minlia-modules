package com.minlia.module.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.entity.SysArticleLabelEntity;
import com.minlia.module.article.bean.ArticleLabelQro;
import com.minlia.module.article.service.SysArticleLabelService;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.util.LocaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "System Article Label Open", description = "文章-标签")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article/label")
public class SysArticleLabelOpenController {

    private final SysArticleLabelService sysArticleLabelService;

    public SysArticleLabelOpenController(SysArticleLabelService sysArticleLabelService) {
        this.sysArticleLabelService = sysArticleLabelService;
    }

    @ApiOperation(value = "编码查询")
    @GetMapping(value = "{code}")
    public Response id(@PathVariable String code) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysArticleLabelEntity>lambdaQuery()
                .eq(SysArticleLabelEntity::getCode, code)
                .eq(SysArticleLabelEntity::getDisFlag, false)
                .eq(SysArticleLabelEntity::getLocale, LocaleUtils.locale());
        return Response.success(sysArticleLabelService.list(queryWrapper));
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody ArticleLabelQro qro) {
        qro.setDisFlag(false);
        qro.setLocale(LocaleUtils.locale());
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysArticleLabelEntity.class))
                .last(qro.getOrderBy());
        return Response.success(sysArticleLabelService.list(queryWrapper));
    }

}