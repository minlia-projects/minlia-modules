package com.minlia.hsjs.integral.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.hsjs.integral.bean.HsjsIntegralRecordQro;
import com.minlia.hsjs.integral.bean.HsjsIntegralUserQro;
import com.minlia.hsjs.integral.constant.HsjsIntegralConstant;
import com.minlia.hsjs.integral.entity.HsjsIntegralConfigEntity;
import com.minlia.hsjs.integral.service.HsjsIntegralUserService;
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
@Api(tags = "HSJS Integral User", description = "积分-用户")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "integral/user")
@RequiredArgsConstructor
public class HsjsIntegralUserController {

    private final HsjsIntegralUserService hsjsIntegralUserService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.User.READ + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(hsjsIntegralUserService.getByUid(MinliaSecurityContextHolder.getUid()));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.User.SELECT + "')")
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody HsjsIntegralUserQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, HsjsIntegralConfigEntity.class));
        return Response.success(hsjsIntegralUserService.list(lambdaQueryWrapper));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + HsjsIntegralConstant.Authorize.User.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody HsjsIntegralUserQro qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, HsjsIntegralConfigEntity.class));
        return Response.success(hsjsIntegralUserService.page(qro.getPage(), lambdaQueryWrapper));
    }

}