//package com.minlia.module.wallet.v1.endpoint;
//
//import com.google.common.collect.Lists;
//import com.minlia.boot.v1.ro.StatefulBody;
//import com.minlia.boot.v1.ro.impl.SuccessResponseBody;
//import com.minlia.boot.v1.web.ApiPrefix;
//import com.minlia.module.data.query.v2.DynamicSpecifications;
//import com.minlia.module.data.query.v2.Operator;
//import com.minlia.module.data.query.v2.QueryCondition;
//import com.minlia.module.data.query.v2.ro.ApiSearchRequestBody;
//import com.minlia.module.security.v1.entity.User;
//import com.minlia.module.security.v1.service.UserQueryService;
//import com.minlia.module.security.v1.utils.SecurityUtils;
//import com.minlia.module.wallet.v1.ro.WithdrawQueryRequestBody;
//import com.minlia.module.wallet.v1.constants.WalletSecurityConstants;
//import com.minlia.module.wallet.v1.entity.WithdrawApply;
//import com.minlia.module.wallet.v1.repository.WithdrawApplyRepository;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.entity.Page;
//import org.springframework.data.entity.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = ApiPrefix.V1 + "withdraw/search")
//@Api(tags = "钱包-提现", description = "钱包")
//@Slf4j
//public class WithdrawSearchEndpoint {
//
//    @Autowired
//    private UserQueryService userQueryService;
//    @Autowired
//    private DynamicSpecifications spec;
//    @Autowired
//    private WithdrawApplyRepository withdrawApplyRepository;
//
//    @PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_read_code + "')")
//    @ApiOperation(value = "我的(分页)", notes = "我的", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody me(@PageableDefault Pageable pageable,@RequestBody ApiSearchRequestBody<WithdrawQueryRequestBody> ro) {
//		ro.getPayload().setUserId(SecurityUtils.getCurrentUser().getId());
//        Page<WithdrawApply> page = withdrawApplyRepository.findAll(spec.buildSpecification(this.builder(ro)),pageable);
//        setUsername(page.getContent());
//        return SuccessResponseBody.builder().payload(page).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_search_code + "')")
//    @ApiOperation(value = "ID查询", notes = "单个查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody findOne(@PathVariable Long id) {
//        WithdrawApply withdrawApply = withdrawApplyRepository.findOne(id);
//        if (null != withdrawApply)
//            setUsername(Lists.newArrayList(withdrawApply));
//        return SuccessResponseBody.builder().payload(withdrawApply).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_search_code + "')")
//    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody list(@RequestBody ApiSearchRequestBody<WithdrawQueryRequestBody> ro) {
//        List<WithdrawApply> withdrawApplies = withdrawApplyRepository.findAll(spec.buildSpecification(this.builder(ro)));
//        setUsername(withdrawApplies);
//        return SuccessResponseBody.builder().payload(withdrawApplies).build();
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('" + WalletSecurityConstants.operate_withdraw_search_code + "')")
//    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "paginated", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody paginated(@PageableDefault Pageable pageable,@RequestBody ApiSearchRequestBody<WithdrawQueryRequestBody> ro) {
//        Page page = withdrawApplyRepository.findAll(spec.buildSpecification(this.builder(ro)),pageable);
//        setUsername(page.getContent());
//        return SuccessResponseBody.builder().payload(page).build();
//    }
//
//    private ApiSearchRequestBody<WithdrawQueryRequestBody> builder(ApiSearchRequestBody<WithdrawQueryRequestBody> ro) {
//        if (null != ro.getPayload().getUserId())
//            ro.getConditions().add(new QueryCondition("userId",Operator.eq,ro.getPayload().getUserId()));
//        if (null != ro.getPayload().getCardholder())
//            ro.getConditions().add(new QueryCondition("cardholder",Operator.eq,ro.getPayload().getCardholder()));
//        if (null != ro.getPayload().getCardNumber())
//            ro.getConditions().add(new QueryCondition("cardNumber",Operator.eq,ro.getPayload().getCardNumber()));
//        if (null != ro.getPayload().getWithdrawStatus())
//            ro.getConditions().add(new QueryCondition("withdrawStatus",Operator.eq,ro.getPayload().getWithdrawStatus()));
//        return ro;
//    }
//
//    private void setUsername(List<WithdrawApply> withdrawApplies) {
//        if (CollectionUtils.isNotEmpty(withdrawApplies)) {
//            for (WithdrawApply withdrawApply:withdrawApplies) {
//                User user = userQueryService.findOne(withdrawApply.getUserId());
//                withdrawApply.setUsername(user.getUsername());
//            }
//        }
//    }
//
//}