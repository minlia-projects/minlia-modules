package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantBusinessTO;
import com.minlia.module.pooul.bean.to.PooulMerchantOwnerTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;
import com.minlia.module.pooul.service.PooulBankCardService;
import com.minlia.module.pooul.service.PooulMerchantService;
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
@Api(tags = "Pooul Merchant", description = "普尔-商户")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/merchant")
public class PooulMerchantEndpoint {

    @Autowired
    private PooulMerchantService pooulMerchantService;

    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response close() {
        PooulMerchantPersonalCTO cto = PooulMerchantPersonalCTO.builder()
                .note("测试")
                .business(PooulMerchantBusinessTO.builder().short_name("候志朋").build())
                .owner(PooulMerchantOwnerTO.builder().idcard_type("1").name("候志朋").build())
                .build();
        Response response = pooulMerchantService.create("100002",cto);
        return response;
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", consumes = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String merchantId) {
        return pooulMerchantService.delete(merchantId);
    }

//    @ApiOperation(value = "默认", notes = "默认", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PutMapping(value = "{merchantId}/{recordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response defalut(@PathVariable String merchantId, @PathVariable Long recordId) {
//        return pooulBankCardService.setDefaultUrl(merchantId, recordId);
//    }
//
//    @ApiOperation(value = "查询所有", notes = "查询所有", httpMethod = "", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping(value = "{merchantId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response all(@PathVariable String merchantId) {
//        return pooulBankCardService.queryAll(merchantId);
//    }

}
