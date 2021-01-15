package com.minlia.module.currency.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.currency.bean.SysCurrencyRateQro;
import com.minlia.module.currency.entity.SysCurrencyRateEntity;
import com.minlia.module.currency.service.SysCurrencyRateService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = ApiPrefix.OPEN + "currency/rate")
public class SysCurrencyRateOpenController {

    private final SysCurrencyRateService sysCurrencyRateService;

    public SysCurrencyRateOpenController(SysCurrencyRateService sysCurrencyRateService) {
        this.sysCurrencyRateService = sysCurrencyRateService;
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysCurrencyRateService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysCurrencyRateQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyRateEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyRateEntity.class)).last(qro.getOrderBy());
        return Response.success(sysCurrencyRateService.list(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysCurrencyRateQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyRateEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyRateEntity.class)).last(qro.getOrderBy());
        return Response.success(sysCurrencyRateService.page(qro.getPage(), queryWrapper));
    }

}