package com.minlia.module.wallet.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.bean.SysWalletAliSro;
import com.minlia.module.wallet.bean.WalletTransferRo;
import com.minlia.module.wallet.constant.WalletConstant;
import com.minlia.module.wallet.service.SysWalletAliService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 钱包-支付宝 前端控制器
 * </p>
 *
 * @author garen
 * @since 2022-01-12
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet/ali")
@Api(tags = "System Wallet Ali", description = "钱包-支付宝账号")
@RequiredArgsConstructor
public class SysWalletAliController {

    private final SysWalletAliService sysWalletAliService;

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.READ + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(sysWalletAliService.getByUid(SecurityContextHolder.getUid()));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.READ + "')")
    @ApiOperation(value = "保存")
    @PostMapping
    public Response create(@Valid @RequestBody SysWalletAliSro sro) {
        return Response.success(sysWalletAliService.save(sro));
    }

}