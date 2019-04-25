package com.minlia.module.pooul.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;
import com.minlia.module.pooul.service.PooulPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 普尔支付
 * Created by garen on 2018/7/23.
 */
@Profile("dev")
@Api(tags = "Pooul Pay", description = "普尔支付")
@RestController
@RequestMapping(value = ApiPrefix.API + "pooul/pay")
public class PooulPayEndpoint {

    @Autowired
    private PooulPayService pooulPayService;

    @ApiOperation(value = "支付", notes = "支付", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response pay(PooulWechatJsminipgTO to) {
//        ro.setPayType(PayTypeEnum.wechat_jsminipg.getName());
//        ro.setNonceStr(NumberGenerator.uuid32());
//        ro.setMchTradeId(RandomStringUtils.randomAlphanumeric(10));     // TODO 订单号需修改，这个只是测试
//        ro.setTotalFee(1);
//        ro.setBody("花果山 Test jsminipg");
//        ro.setSubAppid("wx469ffdb81de47e4d");
//        ro.setSubOpenid("oerQA5Q5clTAK8eA3tGNOAiz7s4o");
//        ro.setNotifyUrl("http://pooul.frp.apartscloud.com/api/open/pooul/notify/pay");
        Response response = pooulPayService.wechatJsminipg(to,"2849928048545130");
        return response;
    }

    @ApiOperation(value = "关闭订单", notes = "关闭订单", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "close", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response close(@RequestParam String mchTradeId) {
        return pooulPayService.close(mchTradeId);
    }

    @ApiOperation(value = "查询订单", notes = "查询订单", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "query", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response query(@RequestParam String mchTradeId) {
        return pooulPayService.query(mchTradeId);
    }

}
