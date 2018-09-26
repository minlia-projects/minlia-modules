package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
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
@Api(tags = "Pooul Bank Card", description = "普尔-银行卡")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/bankcard")
public class PooulBankcardEndpoint {

    @Autowired
    private PooulBankCardService pooulBankCardService;

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@PathVariable String merchantId) {
        PooulBankCardCTO cto = PooulBankCardCTO.builder()
                .account_type(0)
                .owner_name("候志朋")
                .account_num("6226097806221512")
                .bank_full_name("招商银行深圳龙华支行")
                .bank_sub_code("308584001651")
                .cyber_bank_code("308584000013")
                .cmbc_bank(false)
                .build();
        return pooulBankCardService.create(merchantId, cto);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", consumes = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "{merchantId}/{recordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String merchantId, @PathVariable Long recordId) {
        return pooulBankCardService.delete(merchantId, recordId);
    }

    @ApiOperation(value = "默认", notes = "默认", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "{merchantId}/{recordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response defalut(@PathVariable String merchantId, @PathVariable Long recordId) {
        return pooulBankCardService.setDefaultUrl(merchantId, recordId);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有", httpMethod = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response all(@PathVariable String merchantId) {
        return pooulBankCardService.queryAll(merchantId);
    }

}
