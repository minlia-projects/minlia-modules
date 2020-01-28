package com.minlia.module.unified.payment.wechat.v1;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.minlia.module.unified.payment.bean.OrderPaidNotificationResponse;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayPlatformEnum;
import com.minlia.module.unified.payment.event.OrderPaidEventProducer;
import com.minlia.module.unified.payment.util.XmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by will on 9/15/17.
 * api/open/callback/unified/payment/alipay
 */
@RestController
@RequestMapping(value = "api/open/callback/unified/payment/wechat")
@Api(tags = "Unified Payment Callback", description = "统一支付回调接口")
@Slf4j
public class WechatCallbackReceiveEndpoint {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WxPayService wxPayService;

    @RequestMapping
    @ApiOperation(value = "微信回调", notes = "微信回调", httpMethod = "POST")
    public String process(HttpServletRequest request) {
        log.info("微信回调开始-----------------------------");
        String requestedXmlString = XmlUtils.parseRequst(request);
        if (isOfficialNotificationRequest(requestedXmlString)) {
            OrderPaidNotificationResponse body = new OrderPaidNotificationResponse();
            body = mapToBody(requestedXmlString, body);
            OrderPaidEventProducer.onOrderPaid(body);
            log.info("微信回调成功-----------------------------");
            return "SUCCESS";
        } else {
            log.info("微信回调失败-----------------------------");
            return "FAIL";
        }
    }

    private OrderPaidNotificationResponse mapToBody(String requestedXmlString, OrderPaidNotificationResponse body) {
        log.info("微信回调参数解析：{}", requestedXmlString);
        try {
            //这里面已经做了验证签名
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(requestedXmlString);
            log.info("微信原始回调参数：{}", result);
            body.setPaidBy(result.getOpenid());
            body.setAmount(Integer.valueOf(result.getTotalFee().toString()));
            body.setBody(result.getAttach());
            body.setSubject(result.getAttach());
            body.setGatewayTradeNo(result.getTransactionId());
            body.setMerchantTradeNo(result.getOutTradeNo());
            body.setPaidBy(result.getOpenid());
            body.setSign(result.getSign());
            body.setPayPlatform(PayPlatformEnum.ALIPAY);
//            body.setChannel(PayChannelEnum.wechat);
            log.info("微信解析回调参数：{}", body);
        } catch (WxPayException e) {
            log.info("微信回调参数解析異常：" + e.toString());
        }
        return body;
    }

    /**
     * 通过验证签名来校验是否为官方请求过来的回调
     */
    private boolean isOfficialNotificationRequest(String requestedXmlString) {
        log.info("微信回调验签-----------------------------");
        if (StringUtils.isNotEmpty(requestedXmlString)) {
            try {
                WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(requestedXmlString);
                //这里面已经做了验证签名, 所以直接返回真
                return Boolean.TRUE;
            } catch (WxPayException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        } else {
            //非微信请求过来的通知, 忽略
            return Boolean.FALSE;
        }
    }

}
