package com.minlia.module.currency.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.currency.bean.SysCurrencyRateQro;
import com.minlia.module.currency.constant.SysCurrencyConstant;
import com.minlia.module.currency.entity.SysCurrencyRateEntity;
import com.minlia.module.currency.service.SysCurrencyRateService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 货币汇率 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Api(tags = "System Currency Rate", description = "货币-汇率")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "currency/rate")
public class SysCurrencyRateController {

    private final SysCurrencyRateService sysCurrencyRateService;

    public SysCurrencyRateController(SysCurrencyRateService sysCurrencyRateService) {
        this.sysCurrencyRateService = sysCurrencyRateService;
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping
    public Response create(@Valid @RequestBody SysCurrencyRateEntity entity) {
        return Response.success(sysCurrencyRateService.save(entity));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@Valid @RequestBody SysCurrencyRateEntity entity) {
        return Response.success(sysCurrencyRateService.updateById(entity));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(SystemCode.Message.DELETE_SUCCESS, sysCurrencyRateService.removeById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysCurrencyRateService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.SELECT + "')")
    @ApiOperation(value = "计数查询")
    @PostMapping(value = "count")
    public Response count(@Valid @RequestBody SysCurrencyRateQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyRateEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyRateEntity.class));
        return Response.success(sysCurrencyRateService.count(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + SysCurrencyConstant.Authorize.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysCurrencyRateQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyRateEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyRateEntity.class));
        return Response.success(sysCurrencyRateService.page(qro.getPageNumber(), queryWrapper));
    }

}