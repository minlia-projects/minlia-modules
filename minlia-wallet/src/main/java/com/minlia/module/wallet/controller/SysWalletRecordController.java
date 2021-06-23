package com.minlia.module.wallet.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.constant.WalletConstant;
import com.minlia.module.wallet.entity.SysWalletRecordEntity;
import com.minlia.module.wallet.service.SysWalletRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 钱包-记录 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet/record")
@Api(tags = "System Wallet Record", description = "钱包-记录")
@RequiredArgsConstructor
public class SysWalletRecordController {

    private final SysWalletRecordService sysWalletRecordService;

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.READ + "')")
    @ApiOperation(value = "我的(分页)")
    @PostMapping(value = "me")
    public Response historyPage(@Validated @RequestBody QueryRequest qro) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.<SysWalletRecordEntity>lambdaQuery().eq(SysWalletRecordEntity::getUid, SecurityContextHolder.getUid());
        return Response.success(sysWalletRecordService.page(qro.getPage().addOrder(OrderItem.desc("create_date")), lambdaQueryWrapper));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + WalletConstant.Authorize.Withdraw.SELECT + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Validated @RequestBody QueryRequest request) {
        return Response.success(sysWalletRecordService.page(request.getPage().addOrder(OrderItem.desc("create_date"))));
    }

}