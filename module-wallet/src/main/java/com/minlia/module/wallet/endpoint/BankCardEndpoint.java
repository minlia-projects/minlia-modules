package com.minlia.module.wallet.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wallet.bean.to.BankCardCTO;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.to.BankCardUTO;
import com.minlia.module.wallet.bean.vo.BankCardVo;
import com.minlia.module.wallet.constants.WalletSecurityConstant;
import com.minlia.module.wallet.service.BankCardService;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Wallet Bank Card", description = "银行卡")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wallet/bankcard")
public class BankCardEndpoint {

	@Autowired
	private BankCardService bankCardService;
	
    @PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_CREATE_CODE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody create(@Valid @RequestBody BankCardCTO cto) {
		return SuccessResponseBody.builder().payload(bankCardService.create(cto)).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_UPDATE_CODE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody update(@Valid @RequestBody BankCardUTO uto) {
		return SuccessResponseBody.builder().payload(bankCardService.update(uto)).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_DELETE_CODE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody update(@PathVariable Long id) {
		bankCardService.delete(id);
		return SuccessResponseBody.builder().build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_UPDATE_CODE + "')")
	@ApiOperation(value = "设置提现卡号", notes = "设置提现卡号", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "withdraw/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody setWithdrawCard(@PathVariable Long id) {
		bankCardService.setWithdrawCard(id);
		return SuccessResponseBody.builder().build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_READ_CODE + "')")
	@ApiOperation(value = "我的银行卡", notes = "我的银行卡", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "me", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody me() {
		List<BankCardVo> bankCards = bankCardService.queryList(BankCardQO.builder().guid(SecurityContextHolder.getCurrentGuid()).build());
		return SuccessResponseBody.builder().payload(bankCards).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody findOne(@PathVariable Long id) {
		BankCardVo bankCard = bankCardService.queryById(id);
		return SuccessResponseBody.builder().payload(bankCard).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody list(@RequestBody BankCardQO qo) {
		List<BankCardVo> bankCards = bankCardService.queryList(qo);
		return SuccessResponseBody.builder().payload(bankCards).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstant.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody BankCardQO qo) {
		PageInfo pageInfo = bankCardService.queryPage(qo, pageable);
		return SuccessResponseBody.builder().payload(pageInfo).build();
	}

}