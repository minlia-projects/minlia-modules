package com.minlia.module.pooul.endpoint;

import com.auth0.jwt.interfaces.Claim;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.body.pay.PooulPayNotifyData;
import com.minlia.module.pooul.body.pay.PooulPayNotifyResponseBody;
import com.minlia.module.pooul.body.pay.PooulWechatJsminipgRequestBody;
import com.minlia.module.pooul.contract.PooulContracts;
import com.minlia.module.pooul.enumeration.PayType;
import com.minlia.module.pooul.event.PooulEventPublisher;
import com.minlia.module.pooul.service.PooulPayService;
import com.minlia.module.pooul.util.PooulToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * 普尔支付通知
 * Created by garen on 2018/7/23.
 */
@Api(tags = "Pooul Pay Notify", description = "普尔支付通知")
@RestController
@RequestMapping(value = "api/open/pooul/notify")
@Slf4j
public class PooulPayNotifyEndpoint {

    @ApiOperation(value = "通知", notes = "通知", httpMethod = "POST", consumes = MediaType.TEXT_PLAIN_VALUE)
    @PostMapping(value = "pay", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String notify(String token, HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("普尔支付通知:",e);
            //TODO 持久化错误日志
            return "success";
        } finally {
        }

        //公钥验证数据是否合法、返回自定义数据/业务数据
        Map<String,Claim> claims = PooulToken.getClaims(sb.toString());
        PooulPayNotifyResponseBody notifyResponseBody = PooulPayNotifyResponseBody.builder()
                .code(claims.get(PooulContracts.CODE).asInt())
                .nonceStr(claims.get(PooulContracts.NONCE_STR).asString())
                .data(claims.get(PooulContracts.DATA).as(PooulPayNotifyData.class))
                .build();

        //持久化通知记录：历史记录 TODO

        if (notifyResponseBody.isSuccess() && notifyResponseBody.getData().isSuccess()) {
            //成功 、处理后续业务
            PooulEventPublisher.onPaySuccess(notifyResponseBody);
        } else {
            //失败、处理后续业务
            PooulEventPublisher.onPayFailure(notifyResponseBody);
        }
        return "success";
    }

    @Autowired
    private PooulPayService pooulPayService;

    @ApiOperation(value = "", notes = "测试", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "test", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody test() {
        PooulWechatJsminipgRequestBody body = new PooulWechatJsminipgRequestBody();
        body.setPay_type(PayType.wechat_jsminipg.getName());
        body.setNonce_str(NumberGenerator.uuid32());
        body.setMch_trade_id(RandomStringUtils.randomAlphanumeric(10));     // TODO 订单号需修改，这个只是测试
        body.setTotal_fee(1);
        body.setBody("花果山 Test jsminipg");
        body.setSub_appid("wx469ffdb81de47e4d");
        body.setSub_openid("oerQA5Q5clTAK8eA3tGNOAiz7s4o");
        body.setNotify_url("http://pooul.frp.apartscloud.com/api/open/pooul/notify/pay");
        StatefulBody statefulBody = pooulPayService.wechatJsminipg(body);
        return statefulBody;
    }

}
