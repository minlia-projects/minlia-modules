package com.minlia.module.bankcard.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bankcard.ro.BankCardCRO;
import com.minlia.module.bankcard.ro.BankCardQRO;
import com.minlia.module.bankcard.ro.BankCardURO;
import com.minlia.module.bankcard.vo.BankCardVO;
import com.minlia.module.bankcard.constant.BankCardConstants;
import com.minlia.module.bankcard.service.BankCardService;
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

@Api(tags = "Bank Card", description = "银行卡")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bankcard")
public class BankCardEndpoint {

	@Autowired
	private BankCardService bankCardService;
	
    @PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_CREATE_CODE + "')")
	@ApiOperation(value = "", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "bind", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response bind(@Valid @RequestBody BankCardCRO cto) {
		cto.setGuid(SecurityContextHolder.getCurrentGuid());
		return Response.success(bankCardService.create(cto));
	}

    @PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_CREATE_CODE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody BankCardCRO cto) {
		return Response.success(bankCardService.create(cto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_UPDATE_CODE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody BankCardURO uto) {
		return Response.success(bankCardService.update(uto));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_DELETE_CODE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@PathVariable Long id) {
		bankCardService.delete(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_UPDATE_CODE + "')")
	@ApiOperation(value = "设置提现卡号", notes = "设置提现卡号", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "withdraw/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response setWithdrawCard(@PathVariable Long id) {
		bankCardService.setWithdrawCard(id);
		return Response.success();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_READ_CODE + "')")
	@ApiOperation(value = "我的银行卡", notes = "我的银行卡", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "me", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response me() {
		List<BankCardVO> bankCards = bankCardService.queryList(BankCardQRO.builder().guid(SecurityContextHolder.getCurrentGuid()).build());
		return Response.success(bankCards);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response findOne(@PathVariable Long id) {
		BankCardVO bankCard = bankCardService.queryById(id);
		return Response.success(bankCard);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody BankCardQRO qo) {
		List<BankCardVO> bankCards = bankCardService.queryList(qo);
		return Response.success(bankCards);
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankCardConstants.BANKCARD_SEARCH_CODE + "')")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response paginated(@PageableDefault Pageable pageable, @RequestBody BankCardQRO qo) {
		PageInfo pageInfo = bankCardService.queryPage(qo, pageable);
		return Response.success(pageInfo);
	}

}