package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulInternalTransfersTO;
import com.minlia.module.pooul.service.PooulBalancesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2018/9/21.
 */
@Profile("dev")
@Api(tags = "Pooul Balances", description = "普尔-余额")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/balances")
public class PooulBalancesEndpoint {

    @Autowired
    private PooulBalancesService pooulBalancesService;

    @ApiOperation(value = "查询所有", notes = "查询所有", httpMethod = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response all(@PathVariable String merchantId) {
        return Response.success(pooulBalancesService.queryBalances(merchantId));
    }

    @ApiOperation(value = "内部转账", notes = "内部转账", httpMethod = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "internalTransfers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response internalTransfers() {
        PooulInternalTransfersTO transfersTO = PooulInternalTransfersTO.builder()
                .payer_merchant_id("9005381795178490")
                .payee_merchant_id("8426988552549531")
                .voucher("zz00001")
                .amount(574800)
                .transfer_type("结算")
                .op_user_id("10000")
                .build();
        return Response.success(pooulBalancesService.internalTransfers("9005381795178490",transfersTO));
    }

}
