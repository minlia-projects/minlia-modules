package com.minlia.module.bank.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bank.bean.domain.BankBranchDo;
import com.minlia.module.bank.bean.qo.BankBranchQo;
import com.minlia.module.bank.constants.BankSecurityConstant;
import com.minlia.module.bank.service.BankBranchService;
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

@Api(tags = "Bank Branch", description = "联行号")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "bank/branch")
public class BankBranchEndpoint {

	@Autowired
	private BankBranchService bankcodeService;

	@ApiOperation(value = "初始化", notes = "初始化", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "init", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response init() {

//		BankBranchDo bankcodeDo = new BankBranchDo();
//		bankcodeDo.setProvince("asdfdsa");
//		bankcodeService.create(bankcodeDo);

		bankcodeService.init();
		return Response.success();
	}

    @PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_CREATE_CODE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response create(@Valid @RequestBody BankBranchDo bankcodeDo) {
		return Response.success(bankcodeService.create(bankcodeDo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_UPDATE_CODE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@Valid @RequestBody BankBranchDo bankcodeDo) {
		return Response.success(bankcodeService.update(bankcodeDo));
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_DELETE_CODE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response update(@PathVariable String number) {
		bankcodeService.delete(number);
		return Response.success();
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "编号查询", notes = "编号查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "{numbet}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response findOne(@PathVariable String number) {
		BankBranchDo bankCard = bankcodeService.queryByNumber(number);
		return Response.success(bankCard);
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response list(@RequestBody BankBranchQo qo) {
		List<BankBranchDo> bankCards = bankcodeService.queryList(qo);
		return Response.success(bankCards);
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response paginated(@PageableDefault Pageable pageable, @RequestBody BankBranchQo qo) {
		PageInfo pageInfo = bankcodeService.queryPage(qo, pageable);
		return Response.success(pageInfo);
	}

}