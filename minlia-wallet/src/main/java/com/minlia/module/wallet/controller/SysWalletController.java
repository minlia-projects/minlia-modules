package com.minlia.module.wallet.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.constant.WalletConstant;
import com.minlia.module.wallet.service.SysWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 钱包 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet")
@Api(tags = "System Wallet", description = "钱包")
@RequiredArgsConstructor
public class SysWalletController {

    private final SysWalletService sysWalletService;

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.READ + "')")
    @ApiOperation(value = "我的")
    @GetMapping(value = "me")
    public Response me() {
        return Response.success(sysWalletService.getByUid(SecurityContextHolder.getUid()));
    }

    //	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_read_code + "')")
//    @ApiOperation(value = "充值", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "recharge", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response recharge(@Valid @RequestBody WalletRechargeRO ro) {
//        Wallet wallet = walletService.recharge(ro);
//        return Response.success(wallet);
//    }

}