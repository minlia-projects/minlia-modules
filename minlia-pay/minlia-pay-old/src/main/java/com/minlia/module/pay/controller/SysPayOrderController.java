package com.minlia.module.pay.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.pay.constant.SysPayConstants;
import com.minlia.module.pay.service.SysPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 支付-订单 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Api(tags = "System Pay Order", description = "支付-订单")
@Controller
@RequestMapping(value = ApiPrefix.API + "pay/order")
@RequiredArgsConstructor
public class SysPayOrderController {

    private final SysPayOrderService sysPayOrderService;

    //@PreAuthorize(value = "hasAnyAuthority('" + SysPayConstants.Authorize.SELECT + "')")
    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "同步")
    @PostMapping(value = "async/{orderNo}")
    public Response async(@PathVariable String orderNo) {
        return Response.success(sysPayOrderService.async(orderNo));
    }

}