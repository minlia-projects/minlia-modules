package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.service.PooulBankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by garen on 2018/9/21.
 */
@Api(tags = "Pooul Bank Card", description = "普尔-银行卡")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/bankcard")
public class PooulBankcardEndpoint {

    @Autowired
    private PooulBankCardService pooulBankCardService;

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response close(@RequestParam String mchTradeId) {

        PooulBankCardCTO cto = PooulBankCardCTO.builder()
                .account_type(0)
                .owner_name("候志朋")
                .account_num("6226097806221512")
                .bank_full_name("招商银行深圳龙华支行")
                .bank_sub_code("308584001651")
                .cyber_bank_code("308584000013")
                .cmbc_bank(false)
                .build();
        return pooulBankCardService.create(mchTradeId, cto);
    }


}
