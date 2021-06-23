package com.minlia.module.wallet.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.bean.WalletWithdrawApplyRo;
import com.minlia.module.wallet.bean.WalletWithdrawApprovalRo;
import com.minlia.module.wallet.constant.WalletConstant;
import com.minlia.module.wallet.entity.SysWalletWithdrawEntity;
import com.minlia.module.wallet.service.SysWalletWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 钱包-提现 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet/withdraw")
@Api(tags = "System Wallet Withdraw", description = "钱包-提现")
@RequiredArgsConstructor
public class SysWalletWithdrawController {

    private final SysWalletWithdrawService sysWalletWithdrawService;

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.Withdraw.APPLY + "')")
    @ApiOperation(value = "申请", notes = "申请", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "apply")
    public Response apply(@Valid @RequestBody WalletWithdrawApplyRo applyRo) {
        return Response.success(sysWalletWithdrawService.apply(applyRo));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.Withdraw.APPROVAL + "')")
    @ApiOperation(value = "审批")
    @PostMapping(value = "approval")
    public Response approval(@Valid @RequestBody WalletWithdrawApprovalRo approvalRo) {
        return Response.success(sysWalletWithdrawService.approval(approvalRo));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.Withdraw.READ + "')")
    @ApiOperation(value = "我的(分页)")
    @PostMapping(value = "me")
    public Response me(@Validated @RequestBody QueryRequest request) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.<SysWalletWithdrawEntity>lambdaQuery().eq(SysWalletWithdrawEntity::getUid, SecurityContextHolder.getUid());
        return Response.success(sysWalletWithdrawService.page(request.getPage(), lambdaQueryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.Withdraw.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Validated @RequestBody QueryRequest request) {
        return Response.success(sysWalletWithdrawService.page(request.getPage()));
    }

}