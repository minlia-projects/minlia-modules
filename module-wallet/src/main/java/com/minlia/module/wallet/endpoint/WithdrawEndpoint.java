//package com.minlia.module.wallet.v1.endpoint;
//
//import com.minlia.boot.v1.body.StatefulBody;
//import com.minlia.boot.v1.body.impl.SuccessResponseBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.wallet.v1.body.WithdrawApplyRequestBody;
//import com.minlia.module.wallet.v1.body.WithdrawApprovalRequestBody;
//import com.minlia.module.wallet.v1.constants.WalletSecurityConstants;
//import com.minlia.module.wallet.v1.service.WithdrawApplyService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "withdraw")
//@Api(tags = "钱包-提现", description = "钱包")
//@Slf4j
//public class WithdrawEndpoint {
//
//	@Autowired
//	private WithdrawApplyService withdrawApplyService;
//
//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_apply_code + "')")
//	@ApiOperation(value = "申请", notes = "申请", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "apply", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody apply(@Valid @RequestBody WithdrawApplyRequestBody requestBody) {
//		return SuccessResponseBody.builder().payload(withdrawApplyService.apply(requestBody)).build();
//	}
//
//	@PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_approval_code + "')")
//	@ApiOperation(value = "审批", notes = "审批", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(value = "approval", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public StatefulBody approval(@Valid @RequestBody WithdrawApprovalRequestBody requestBody) {
//		return SuccessResponseBody.builder().payload(withdrawApplyService.approval(requestBody)).build();
//	}
//
//}