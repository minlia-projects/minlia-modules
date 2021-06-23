package com.minlia.module.currency.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.currency.bean.SysCurrencyQro;
import com.minlia.module.currency.entity.SysCurrencyEntity;
import com.minlia.module.currency.service.SysCurrencyService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 货币 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Api(tags = "System Currency", description = "货币")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "currency")
public class SysCurrencyOpenController {

    private final SysCurrencyService sysCurrencyService;

    public SysCurrencyOpenController(SysCurrencyService sysCurrencyService) {
        this.sysCurrencyService = sysCurrencyService;
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(sysCurrencyService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysCurrencyQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyEntity.class));
        return Response.success(sysCurrencyService.list(queryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysCurrencyQro qro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysCurrencyEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysCurrencyEntity.class));
        return Response.success(sysCurrencyService.page(qro.getPageNumber(), queryWrapper));
    }

}