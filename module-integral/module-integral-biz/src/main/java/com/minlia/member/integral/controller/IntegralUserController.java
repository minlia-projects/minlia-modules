package com.minlia.member.integral.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.member.integral.bean.IntegralUserQro;
import com.minlia.member.integral.constant.IntegralConstant;
import com.minlia.member.integral.entity.IntegralConfigEntity;
import com.minlia.member.integral.service.IntegralUserService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 积分-用户 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Api(tags = "System Integral User", description = "积分-用户")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "integral/user")
@RequiredArgsConstructor
public class IntegralUserController {

    private final IntegralUserService integralUserService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.User.READ + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(integralUserService.getByUid(MinliaSecurityContextHolder.getUid()));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.User.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody IntegralUserQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralUserService.list(lambdaQueryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + IntegralConstant.Authorize.User.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody IntegralUserQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, IntegralConfigEntity.class));
        return Response.success(integralUserService.page(qro.getPage(), lambdaQueryWrapper));
    }

}