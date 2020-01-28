package com.minlia.module.unified.payment.alipay.v1;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.minlia.module.unified.payment.bean.OrderPaidNotificationResponse;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayPlatformEnum;
import com.minlia.module.unified.payment.event.OrderPaidEventProducer;
import com.minlia.module.unified.payment.util.RequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by will on 9/15/17.
 * api/open/callback/unified/payment/alipay
 */
@Slf4j
@RestController
@RequestMapping(value = "api/open/callback/unified/payment/alipay")
@Api(tags = "Unified Payment Callback", description = "统一支付回调接口")
public class AlipayCallbackReceiveEndpoint {

    @Autowired
    private AlipayConfig alipayConfig;

    @RequestMapping
    @ApiOperation(value = "支付宝回调", notes = "支付宝回调", httpMethod = "POST")
    public String process(HttpServletRequest request) {
        log.info("支付宝回调开始-----------------------------");
        if (isOfficialNotificationRequest(request)) {
            log.info("支付宝回调成功-----------------------------");
            OrderPaidNotificationResponse body = new OrderPaidNotificationResponse();
            body = mapToBody(request, body);
            OrderPaidEventProducer.onOrderPaid(body);
            return "success";
        } else {
            log.info("支付宝回调失败-----------------------------");
            return "fail";
        }
    }

    private OrderPaidNotificationResponse mapToBody(HttpServletRequest request, OrderPaidNotificationResponse body) {
        Map<String, String> underScoreKeyMap = RequestUtils.getStringParams(request);
        Map<String, String> camelCaseKeyMap = RequestUtils.convertKeyToCamelCase(underScoreKeyMap);
        String jsonStr = JSON.toJSONString(camelCaseKeyMap);

        AlipayNotification requestBody = JSON.parseObject(jsonStr, AlipayNotification.class);
        body.setMerchantTradeNo(requestBody.getOutTradeNo());
        body.setGatewayTradeNo(requestBody.getTradeNo());
        body.setPayPlatform(PayPlatformEnum.ALIPAY);
//        body.setChannel(PayChannelEnum.alipay);
        return body;
    }

    public static void main(String[] args) {
        AlipayNotification body = new AlipayNotification();
        body.setTradeNo("2019022522001491651029770879");

        OrderPaidNotificationResponse notificationBody = new OrderPaidNotificationResponse();
        notificationBody.setMerchantTradeNo(body.getTradeNo());
        log.info("支付宝第三方回调参数3：{}", body.toString());
    }

    private Boolean isOfficialNotificationRequest(HttpServletRequest request) {
        log.info("支付宝回调验签-----------------------------");
        //获取支付宝POST过来反馈信息
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);

            }
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            try {
                boolean flag = AlipaySignature.rsaCheckV1(params, alipayConfig.getCertificate().getPlatformPublicKey(), AlipayConstants.CHARSET_UTF8, "RSA2");
                if (flag) {
                    return Boolean.TRUE;
                } else {
                    log.error("验签失败");
                    return Boolean.FALSE;
                }
            } catch (Exception e) {
                log.error("验签失败");
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }
}
