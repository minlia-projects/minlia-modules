package com.minlia.module.i18n.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.i18n.Lang;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.bean.I18nQRO;
import com.minlia.module.i18n.bean.I18nSro;
import com.minlia.module.i18n.constant.I18nConstants;
import com.minlia.module.i18n.entity.I18nEntity;
import com.minlia.module.i18n.event.I18nReloadEvent;
import com.minlia.module.i18n.resource.MessageSource;
import com.minlia.module.i18n.service.I18nService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 国际化 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-19
 */
@Api(tags = "System I18n", description = "系统国际化")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "i18n")
public class I18nController {

    @Autowired
    private Mapper mapper;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private MessageSource messageSource;

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody I18nSro sro) {
        return Response.success(i18nService.save(mapper.map(sro, I18nEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody I18nSro sro) {
        return Response.success(i18nService.updateById(mapper.map(sro, I18nEntity.class)));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(i18nService.removeById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_DELETE + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reload")
    public Response reload() {
        messageSource.reload();
        I18nReloadEvent.onReload();
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_CREATE + "')")
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "test/{i18nkey:.+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response test(@PathVariable String i18nkey) {
        return Response.success(Lang.get(i18nkey));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryOne(@PathVariable Long id) {
        return Response.success(i18nService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response queryList(@Valid @RequestBody I18nQRO qro) {
        LambdaQueryWrapper<I18nEntity> queryWrapper = new QueryWrapper<I18nEntity>()
                .lambda()
                .setEntity(DozerUtils.map(qro, I18nEntity.class));
        return Response.success(i18nService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + I18nConstants.SEC_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response queryPage(@Valid @RequestBody I18nQRO qro) {
        LambdaQueryWrapper<I18nEntity> queryWrapper = new QueryWrapper<I18nEntity>()
                .lambda()
                .setEntity(DozerUtils.map(qro, I18nEntity.class));
        Page<I18nEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(i18nService.page(page, queryWrapper));
//        return PageHelper.startPage(qro.getPageNumber(),qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()->i18nMapper.queryList(qro));
    }

}

