package com.minlia.hsjs.integral.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.hsjs.integral.bean.HsjsIntegralConfigCro;
import com.minlia.hsjs.integral.bean.HsjsIntegralConfigQro;
import com.minlia.hsjs.integral.bean.HsjsIntegralConfigUro;
import com.minlia.hsjs.integral.constant.HsjsIntegralConstant;
import com.minlia.hsjs.integral.entity.HsjsIntegralConfigEntity;
import com.minlia.hsjs.integral.service.HsjsIntegralConfigService;
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
@Api(tags = "HSJS Integral Config", description = "积分-配置")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "integral/config")
@RequiredArgsConstructor
public class HsjsIntegralConfigController {

    private final HsjsIntegralConfigService hsjsIntegralConfigService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.CREATE + "')")
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody HsjsIntegralConfigCro cro) {
        return Response.success(hsjsIntegralConfigService.save(DozerUtils.map(cro, HsjsIntegralConfigEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.UPDATE + "')")
    @ApiOperation(value = "修改")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody HsjsIntegralConfigUro uro) {
        return Response.success(hsjsIntegralConfigService.updateById(DozerUtils.map(uro, HsjsIntegralConfigEntity.class)));
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(hsjsIntegralConfigService.removeById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response id(@PathVariable Long id) {
        return Response.success(hsjsIntegralConfigService.getById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody HsjsIntegralConfigQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, HsjsIntegralConfigEntity.class));
        return Response.success(hsjsIntegralConfigService.list(lambdaQueryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.Config.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody HsjsIntegralConfigQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, HsjsIntegralConfigEntity.class));
        return Response.success(hsjsIntegralConfigService.page(qro.getPage(), lambdaQueryWrapper));
    }

}