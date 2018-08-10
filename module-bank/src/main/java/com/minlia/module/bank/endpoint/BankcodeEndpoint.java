package com.minlia.module.bank.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.bank.constants.BankSecurityConstant;
import com.minlia.module.bank.domain.BankcodeDo;
import com.minlia.module.bank.dto.BankcodeQueryDto;
import com.minlia.module.bank.service.BankcodeService;
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
@RequestMapping(value = ApiPrefix.V1 + "bankcode")
public class BankcodeEndpoint {

	@Autowired
	private BankcodeService bankcodeService;


	@ApiOperation(value = "初始化", notes = "初始化", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "init", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody init() {

//		BankcodeDo bankcodeDo = new BankcodeDo();
//		bankcodeDo.setProvince("asdfdsa");
//		bankcodeService.create(bankcodeDo);

//		bankcodeService.init();
		return SuccessResponseBody.builder().build();
	}

    @PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_CREATE_CODE + "')")
	@ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody create(@Valid @RequestBody BankcodeDo bankcodeDo) {
		return SuccessResponseBody.builder().payload(bankcodeService.create(bankcodeDo)).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_UPDATE_CODE + "')")
	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody update(@Valid @RequestBody BankcodeDo bankcodeDo) {
		return SuccessResponseBody.builder().payload(bankcodeService.update(bankcodeDo)).build();
	}

	@PreAuthorize(value = "hasAnyAuthority('" + BankSecurityConstant.BANK_DELETE_CODE + "')")
	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody update(@PathVariable String number) {
		bankcodeService.delete(number);
		return SuccessResponseBody.builder().build();
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "编号查询", notes = "编号查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "{numbet}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody findOne(@PathVariable String number) {
		BankcodeDo bankCard = bankcodeService.queryByNumber(number);
		return SuccessResponseBody.builder().payload(bankCard).build();
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody list(@RequestBody BankcodeQueryDto dto) {
		List<BankcodeDo> bankCards = bankcodeService.queryList(dto);
		return SuccessResponseBody.builder().payload(bankCards).build();
	}

	@PreAuthorize(value = "isAuthenticated()")
	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public StatefulBody paginated(@PageableDefault Pageable pageable, @RequestBody BankcodeQueryDto dto) {
		PageInfo pageInfo = bankcodeService.queryPage(dto, pageable);
		return SuccessResponseBody.builder().payload(pageInfo).build();
	}

}