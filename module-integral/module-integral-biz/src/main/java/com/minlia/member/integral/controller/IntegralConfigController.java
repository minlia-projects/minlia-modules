package com.minlia.member.integral.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.member.integral.bean.IntegralConfigCro;
import com.minlia.member.integral.bean.IntegralConfigQro;
import com.minlia.member.integral.bean.IntegralConfigUro;
import com.minlia.member.integral.constant.IntegralConstant;
import com.minlia.member.integral.entity.IntegralConfigEntity;
import com.minlia.member.integral.service.IntegralConfigService;
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
 * 积分-配置 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Api(tags = "System Integral Config", description = "积分-配置")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "integral/config")
@RequiredArgsConstructor
public class IntegralConfigController {

    private final IntegralConfigService integralConfigService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody IntegralConfigCro cro) {
        return Response.success(integralConfigService.save(DozerUtils.map(cro, IntegralConfigEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody IntegralConfigUro uro) {
        return Response.success(integralConfigService.updateById(DozerUtils.map(uro, IntegralConfigEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(integralConfigService.removeById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(integralConfigService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody IntegralConfigQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralConfigService.list(lambdaQueryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody IntegralConfigQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralConfigService.page(qro.getPage(), lambdaQueryWrapper));
    }

}