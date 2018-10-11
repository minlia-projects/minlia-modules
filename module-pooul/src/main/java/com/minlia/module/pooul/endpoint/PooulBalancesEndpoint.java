package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.service.PooulBalancesService;
import com.minlia.module.pooul.service.PooulBankCardService;
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

}
