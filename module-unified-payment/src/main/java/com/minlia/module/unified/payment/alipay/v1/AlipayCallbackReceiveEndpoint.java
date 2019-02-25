package com.minlia.module.unified.payment.alipay.v1;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.minlia.module.unified.payment.event.OrderPaidEventProducer;
import com.minlia.module.unified.payment.body.OrderPaidNotificationBody;
import com.minlia.module.unified.payment.body.PayType;
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
        if (isOfficialNotificationRequest(request)) {
            OrderPaidNotificationBody body = new OrderPaidNotificationBody();
            body = mapToBody(request,body);
            log.info("支付宝第三方回调参数3：{}", body);
            OrderPaidEventProducer.onOrderPaid(body);
            return "success";
        } else {
            return "fail";
        }
    }

    private OrderPaidNotificationBody mapToBody(HttpServletRequest request,OrderPaidNotificationBody body) {
        Map<String, String> underScoreKeyMap = RequestUtils.getStringParams(request);
        Map<String, String> camelCaseKeyMap = RequestUtils.convertKeyToCamelCase(underScoreKeyMap);
        String jsonStr = JSON.toJSONString(camelCaseKeyMap);

        log.info("支付宝第三方回调参数1：{}", jsonStr);

        AlipayNotification requestBody = JSON.parseObject(jsonStr, AlipayNotification.class);
        log.info("支付宝第三方回调参数2：{}", requestBody);
        body.setMerchantTradeNo(requestBody.getTradeNo());
        body.setPayType(PayType.ALIPAY);
        return body;
    }

    private Boolean isOfficialNotificationRequest(HttpServletRequest request) {
//        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
//            Enumeration<?> pNames = request.getParameterNames();
//            Map<String, String> param = new HashMap<String, String>();
//            try {
//                while (pNames.hasMoreElements()) {
//                    String pName = (String) pNames.nextElement();
//                    param.put(pName,request.getParameter(pName));
//                }
//                ObjectMapper objectMapper = new ObjectMapper();
//                objectMapper.setPropertyNamingStrategy(new LowerCaseStrategy());
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//
//                String paramJson = objectMapper.writeValueAsString(param);
//                log.debug(paramJson);
//                log.debug("验证前商品总价和: " + param.get("totleAmount"));
//                log.debug(param.toString());
//                boolean signVerified = AlipaySignature
//                        .rsaCheckV2(param,config.getCertificate().getPlatformPublicKey(),
//                                AlipayConstants.CHARSET_UTF8); // 校验签名是否正确
//                if (signVerified) {
//                    return Boolean.TRUE;
//                } else {
//                    log.debug("验签失败");
//                    return Boolean.FALSE;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return Boolean.FALSE;
//            }
//
//
//        }
//        return Boolean.FALSE;
//    }
//


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
                params.put(name,valueStr);

            }
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            try {
                boolean flag = AlipaySignature.rsaCheckV1(params,alipayConfig.getCertificate().getPlatformPublicKey(),AlipayConstants.CHARSET_UTF8,"RSA2");

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
