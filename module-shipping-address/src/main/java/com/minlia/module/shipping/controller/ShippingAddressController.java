package com.minlia.module.shipping.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.shipping.bean.ShippingAddressQro;
import com.minlia.module.shipping.bean.ShippingAddressSro;
import com.minlia.module.shipping.constant.ShippingAddressConstants;
import com.minlia.module.shipping.entity.ShippingAddressEntity;
import com.minlia.module.shipping.enums.ExpressAddressTypeEnum;
import com.minlia.module.shipping.service.ShippingAddressService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 收货地址 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-01-04
 */
@Api(tags = "System Shipping Address", description = "收货地址")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/shipping/address")
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    public ShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.CREATE + "')")
    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "创建")
    @PostMapping(value = "")
    public Response create(@Valid @RequestBody ShippingAddressSro sro) {
        return Response.success(shippingAddressService.create(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.UPDATE + "')")
    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @ApiOperation(value = "修改")
    @PutMapping(value = "")
    public Response update(@Valid @RequestBody ShippingAddressSro sro) {
        return Response.success(shippingAddressService.update(sro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.DELETE + "')")
    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(shippingAddressService.remove(Wrappers.<ShippingAddressEntity>lambdaQuery().eq(ShippingAddressEntity::getId, id).eq(ShippingAddressEntity::getUid, MinliaSecurityContextHolder.getUid())));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response create(@PathVariable Long id) {
        return Response.success(shippingAddressService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "默认地址")
    @GetMapping(value = "default/{type}")
    public Response def(@PathVariable ExpressAddressTypeEnum type) {
        return Response.success(shippingAddressService.getDefault(type));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody ShippingAddressQro qro) {
        qro.setUid(MinliaSecurityContextHolder.getUid());
        LambdaQueryWrapper<ShippingAddressEntity> queryWrapper = Wrappers.<ShippingAddressEntity>lambdaQuery()
                .setEntity(DozerUtils.map(qro, ShippingAddressEntity.class));
        return Response.success(shippingAddressService.list(queryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + ShippingAddressConstants.Authorize.SEARCH + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody ShippingAddressQro qro) {
        qro.setUid(MinliaSecurityContextHolder.getUid());
        LambdaQueryWrapper<ShippingAddressEntity> queryWrapper = Wrappers.<ShippingAddressEntity>lambdaQuery()
                .setEntity(DozerUtils.map(qro, ShippingAddressEntity.class));
        return Response.success(shippingAddressService.page(qro.getPage(), queryWrapper));
    }

}