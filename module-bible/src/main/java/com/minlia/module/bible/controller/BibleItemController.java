package com.minlia.module.bible.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.bible.bean.BibleItemCro;
import com.minlia.module.bible.bean.BibleItemQro;
import com.minlia.module.bible.bean.BibleItemUro;
import com.minlia.module.bible.constant.BibleConstants;
import com.minlia.module.bible.entity.BibleItemEntity;
import com.minlia.module.bible.service.BibleItemService;
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
@Api(tags = "System Bible Item", description = "数据字典")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bible/item")
public class BibleItemController {

    private final BibleItemService bibleItemService;

    public BibleItemController(BibleItemService bibleItemService) {
        this.bibleItemService = bibleItemService;
    }

    @AuditLog(value = "create system bible item", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping()
    public Response create(@Valid @RequestBody BibleItemCro cto) {
        return Response.success(bibleItemService.create(cto));
    }

    @AuditLog(value = "update system bible item", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.UPDATE + "')")
    @ApiOperation(value = "更新")
    @PutMapping()
    public Response update(@Valid @RequestBody BibleItemUro uto) {
        return Response.success(bibleItemService.update(uto));
    }

    @AuditLog(value = "delete system bible item by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(bibleItemService.delete(id));
    }

    @AuditLog(value = "query system bible item by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(bibleItemService.getById(id));
    }

    @AuditLog(value = "query system bible item by code", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "CODE查询")
    @GetMapping(value = "get")
    public Response get(@RequestParam String parentCode, @RequestParam String code) {
        return Response.success(SystemCode.Message.SUCCESS, bibleItemService.get(parentCode, code));
    }
//
//    @AuditLog(value = "query system bible item by parent code", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
//    @ApiOperation(value = "父CODE查询")
//    @GetMapping(value = "queryByParentCode")
//    public Response queryByParentCode(@RequestParam String parentCode) {
//        return Response.success(bibleItemService.list(BibleItemEntity.builder().parentCode(parentCode).build()));
//    }

    @AuditLog(value = "query system bible item by body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "查询集合")
    @GetMapping(value = "list")
    public Response queryList(@Valid @RequestBody BibleItemQro qro) {
        return Response.success(bibleItemService.list(DozerUtils.map(qro, BibleItemEntity.class)));
    }

    @AuditLog(value = "query system bible item by body as page", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody BibleItemQro qro) {
        Page page = new Page<>(qro.getPageNumber(), qro.getPageSize());
        return Response.success(bibleItemService.page(page, DozerUtils.map(qro, BibleItemEntity.class)));
    }

}