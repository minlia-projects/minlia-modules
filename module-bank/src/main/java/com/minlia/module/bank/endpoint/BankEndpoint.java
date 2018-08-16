package com.minlia.module.bank.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bank.bean.domain.BankDo;
import com.minlia.module.bank.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Bank", description = "银行")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bank")
public class BankEndpoint {

	@Autowired
	private BankService bankService;
	
//    @PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_CREATE_CODE + "')")
//	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody create(@Valid @RequestBody BankDo bankDo) {
//		return SuccessResponseBody.builder().payload(bankService.create(bankDo)).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_UPDATE_CODE + "')")
//	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody update(@Valid @RequestBody BankDo bankDo) {
//		return SuccessResponseBody.builder().payload(bankService.update(bankDo)).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_DELETE_CODE + "')")
//	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
//	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody update(@PathVariable String number) {
//		bankService.delete(number);
//		return SuccessResponseBody.builder().build();
//	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody list() {
		List<BankDo> bankDos = bankService.queryList();
		return SuccessResponseBody.builder().payload(bankDos).build();
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody paginated(@PageableDefault Pageable pageable) {
		PageInfo pageInfo = bankService.queryPage(pageable);
		return SuccessResponseBody.builder().payload(pageInfo).build();
	}

}