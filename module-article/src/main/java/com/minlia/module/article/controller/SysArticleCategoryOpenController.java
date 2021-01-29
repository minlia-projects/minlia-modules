package com.minlia.module.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.article.entity.SysArticleCategoryEntity;
import com.minlia.module.article.bean.ArticleCategoryQro;
import com.minlia.module.article.service.SysArticleCategoryService;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.util.LocaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
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
@Api(tags = "System Article Category OPEN", description = "文章-类目")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "article/category")
public class SysArticleCategoryOpenController {

    private final SysArticleCategoryService sysArticleCategoryService;

    public SysArticleCategoryOpenController(SysArticleCategoryService sysArticleCategoryService) {
        this.sysArticleCategoryService = sysArticleCategoryService;
    }

    @ApiOperation(value = "编码查询")
    @GetMapping(value = "{code}")
    public Response code(@PathVariable String code) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysArticleCategoryEntity>lambdaQuery().eq(SysArticleCategoryEntity::getCode, code).eq(SysArticleCategoryEntity::getLocale, LocaleUtils.locale()).eq(SysArticleCategoryEntity::getDisFlag, false);
        SysArticleCategoryEntity entity = sysArticleCategoryService.getOne(queryWrapper);
//        if (null != entity) {
//            entity.setArticles(sysArticleCategoryService.queryArticleByCategoryId(articleCategory.getId()));
//        }
        return Response.success(entity);
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody ArticleCategoryQro qro) {
        qro.setDisFlag(false);
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleCategoryEntity.class));
        List<SysArticleCategoryEntity> list = sysArticleCategoryService.list(queryWrapper);
        setChildren(list);
        return Response.success(list);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ArticleCategoryQro qro) {
        qro.setDisFlag(false);
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleCategoryEntity.class));
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
            LambdaQueryWrapper queryWrapper = Wrappers.<SysArticleCategoryEntity>lambdaQuery().eq(SysArticleCategoryEntity::getParentId, entity.getId()).eq(SysArticleCategoryEntity::getLocale, LocaleUtils.locale()).eq(SysArticleCategoryEntity::getDisFlag, false);
            List<SysArticleCategoryEntity> children = sysArticleCategoryService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(children)) {
                entity.setChildren(children);
                children.stream().forEach(category -> setChildren(category));
            }
        }
    }

}