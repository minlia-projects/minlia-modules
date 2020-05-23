package com.minlia.module.wallet.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.wallet.bean.entity.Wallet;
import com.minlia.module.wallet.bean.ro.WalletRechargeRO;
import com.minlia.module.wallet.service.WalletHistoryService;
import com.minlia.module.wallet.service.WalletService;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet")
@Api(tags = "System Wallet", description = "钱包")
public class WalletEndpoint {

	@Autowired
	private WalletService walletService;

	@Autowired
    private WalletHistoryService walletHistoryService;

//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_read_code + "')")
	@ApiOperation(value = "我的钱包", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "me", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response me() {
		Wallet wallet = walletService.queryByGuid(SecurityContextHolder.getCurrentGuid());
		return Response.success(wallet);
	}

//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_read_code + "')")
	@ApiOperation(value = "操作历史", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "history/page", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response historyPage(@RequestBody QueryRequest qro) {
        Wallet wallet = walletService.queryByGuid(SecurityContextHolder.getCurrentGuid());
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> walletHistoryService.queryByWalletId(wallet.getId()));
        return Response.success(pageInfo);
	}

//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_read_code + "')")
	@ApiOperation(value = "充值", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "recharge", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response recharge(@Valid @RequestBody WalletRechargeRO ro) {
		Wallet wallet = walletService.recharge(ro);
		return Response.success(wallet);
	}


////	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody findOne(@PathVariable Long id) {
//		Wallet wallet = walletRepository.findOne(id);
//		return SuccessResponseBody.builder().payload(wallet).build();
//	}
//
////	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody list(@RequestBody ApiSearchRequestBody<WalletQueryRequestBody> ro) {
//		List<Wallet> wallets = walletRepository.findAll(spec.buildSpecification(ro));
//		return SuccessResponseBody.builder().payload(wallets).build();
//	}
//
////	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "paginated", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<WalletQueryRequestBody> ro) {
//		Page page = walletRepository.findAll(spec.buildSpecification(ro), pageable);
//		return SuccessResponseBody.builder().payload(page).build();
//	}

}