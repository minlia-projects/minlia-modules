//package com.minlia.module.wallet.v1.endpoint;
//
//import com.minlia.boot.v1.bean.StatefulBody;
//import com.minlia.boot.v1.bean.impl.SuccessResponseBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.data.query.v2.DynamicSpecifications;
//import com.minlia.module.data.query.v2.bean.ApiSearchRequestBody;
//import com.minlia.module.wallet.v1.bean.WalletQueryRequestBody;
//import com.minlia.module.wallet.v1.constants.WalletSecurityConstants;
//import com.minlia.module.wallet.v1.domain.Wallet;
//import com.minlia.module.wallet.v1.repository.WalletRepository;
//import com.minlia.module.wallet.v1.service.WalletService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "wallet/search")
//@Api(tags = "钱包", description = "钱包")
//@Slf4j
//public class WalletSearchEndpoint {
//
//	@Autowired
//	private DynamicSpecifications spec;
//
//	@Autowired
//	private WalletService walletService;
//
//	@Autowired
//	private WalletRepository walletRepository;
//
////	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_read_code + "')")
//	@ApiOperation(value = "我的钱包", notes = "我的钱包", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping(value = "me", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody me() {
//		Wallet wallet = walletService.findOneByOwner();
//		return SuccessResponseBody.builder().payload(wallet).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody findOne(@PathVariable Long id) {
//		Wallet wallet = walletRepository.findOne(id);
//		return SuccessResponseBody.builder().payload(wallet).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody list(@RequestBody ApiSearchRequestBody<WalletQueryRequestBody> bean) {
//		List<Wallet> wallets = walletRepository.findAll(spec.buildSpecification(bean));
//		return SuccessResponseBody.builder().payload(wallets).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_wallet_search_code + "')")
//	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "paginated", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody ApiSearchRequestBody<WalletQueryRequestBody> bean) {
//		Page page = walletRepository.findAll(spec.buildSpecification(bean), pageable);
//		return SuccessResponseBody.builder().payload(page).build();
//	}
//
//}