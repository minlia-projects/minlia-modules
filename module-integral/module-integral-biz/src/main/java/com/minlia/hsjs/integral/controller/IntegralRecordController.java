package com.minlia.hsjs.integral.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.hsjs.integral.bean.IntegralRecordQro;
import com.minlia.hsjs.integral.constant.IntegralConstant;
import com.minlia.hsjs.integral.entity.IntegralConfigEntity;
import com.minlia.hsjs.integral.service.IntegralRecordService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 积分-记录 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Api(tags = "HSJS Integral Record", description = "积分-记录")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "integral/record")
@RequiredArgsConstructor
public class IntegralRecordController {

    private final IntegralRecordService integralRecordService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Record.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(integralRecordService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Record.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody IntegralRecordQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralRecordService.list(lambdaQueryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Record.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody IntegralRecordQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralRecordService.page(qro.getPage(), lambdaQueryWrapper));
    }

}