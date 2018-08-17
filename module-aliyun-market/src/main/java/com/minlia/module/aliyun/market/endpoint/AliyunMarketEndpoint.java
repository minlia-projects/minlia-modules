package com.minlia.module.aliyun.market.endpoint;

import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.aliyun.market.bean.dto.BankCardVerifyDto;
import com.minlia.module.aliyun.market.bean.to.BankCardVerifyTo;
import com.minlia.module.aliyun.market.utils.AliyunMarketUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiPrefix.API + "")
@Api(tags = "Aliyun Market", description = "云市场")
public class AliyunMarketEndpoint {

	@ApiOperation(value = "银行卡二、三、四要素验证", notes = "验证", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "bankcard/verify/{appcode}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public BankCardVerifyDto verifyBankcard(@PathVariable String appcode,@Valid @RequestBody BankCardVerifyTo to) {
		return AliyunMarketUtils.verifyBankCard(appcode,to);
	}

}