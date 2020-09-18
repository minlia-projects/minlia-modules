package com.minlia.module.bible.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.bible.bean.BibleQro;
import com.minlia.module.bible.bean.BibleSro;
import com.minlia.module.bible.constant.BibleConstants;
import com.minlia.module.bible.entity.BibleEntity;
import com.minlia.module.bible.service.BibleService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Api(tags = "System Bible", description = "数据字典")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bible")
public class BibleController {

    private final BibleService bibleService;

    public BibleController(BibleService bibleService) {
        this.bibleService = bibleService;
    }

    @AuditLog(value = "refresh system bible config", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
    @ApiOperation(value = "刷新配置")
    @PostMapping(value = "refresh/config")
    public Response resetConfig() {
        bibleService.reload();
        return Response.success();
    }

    @AuditLog(value = "create system bible", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody BibleSro sro) {
        return Response.success(bibleService.create(sro));
    }

    @AuditLog(value = "update system bible", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody BibleSro sro) {
        return Response.success(bibleService.update(sro));
    }

    @AuditLog(value = "delete system bible by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(bibleService.delete(id));
    }

    @AuditLog(value = "query system bible by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(bibleService.queryById(id));
    }

    @AuditLog(value = "query system bible by code", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "CODE查询")
    @GetMapping(value = "code")
    public Response code(@RequestParam String code) {
        return Response.success(bibleService.queryByCode(code));
    }

    @AuditLog(value = "query system bible by body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody BibleQro qro) {
        return Response.success(bibleService.list(DozerUtils.map(qro, BibleEntity.class)));
    }

    @AuditLog(value = "query system bible by body as page", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody BibleQro qro) {
        Page<BibleEntity> page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(bibleService.page(DozerUtils.map(qro, BibleEntity.class), page));
    }

}

