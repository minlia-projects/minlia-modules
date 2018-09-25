package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.qo.PooulCmbaYqQO;
import com.minlia.module.pooul.bean.to.PooulWithdrawTO;
import com.minlia.module.pooul.service.PooulWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 普尔支付
 * Created by garen on 2018/7/23.
 */
@Api(tags = "Pooul Withdraw", description = "普尔转账")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/withdraw")
public class PooulWithdrawEndpoint {

    @Autowired
    private PooulWithdrawService pooulWithdrawService;

    @ApiOperation(value = "提现", notes = "提现", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response pay(@RequestParam String merchantId, @RequestBody PooulWithdrawTO withdrawTO) {
        withdrawTO = PooulWithdrawTO.builder()
                .mch_withdraw_id("WD00005")
                .withdraw_type(1)
                .bank_card_id(261)
                .local_flag(5)
                .amount(1)
                .trade_fee(0)
                .voucher("WD00002")
                .remarks("提现")
                .op_user_id("-1")
                .build();
        Response response = pooulWithdrawService.withdraw(merchantId, withdrawTO);
        return response;
    }

    @ApiOperation(value = "查询", notes = "查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{merchant_id}/{mch_withdraw_id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response fund_account(@PathVariable String merchant_id, @PathVariable String mch_withdraw_id) {
        return pooulWithdrawService.query(merchant_id, mch_withdraw_id);
    }

//    @ApiOperation(value = "查询", notes = "查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "fund_account", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response fund_account(@Valid @RequestBody PooulCmbaYqQO qo) {
//        return pooulWithdrawService.fundAccount(qo);
//    }

//    @ApiOperation(value = "关闭订单", notes = "关闭订单", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping(value = "close", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response close(@RequestParam String mchTradeId) {
//        return pooulPayService.close(mchTradeId);
//    }
//


}
