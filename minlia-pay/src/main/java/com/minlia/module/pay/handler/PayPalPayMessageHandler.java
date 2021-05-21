package com.minlia.module.pay.handler;

import com.alibaba.fastjson.JSON;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayMessage;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.paypal.api.PayPalPayService;
import com.minlia.module.pay.service.SysPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PayPal支付回调处理器
 * Created by ZaoSheng on 2016/6/1.
 */
@Slf4j
@Component
public class PayPalPayMessageHandler implements PayMessageHandler<PayMessage, PayPalPayService> {

    private final SysPayOrderService sysPayOrderService;

    public PayPalPayMessageHandler(SysPayOrderService sysPayOrderService) {
        this.sysPayOrderService = sysPayOrderService;
    }

    @Override
    public PayOutMessage handle(PayMessage payMessage, Map<String, Object> context, PayPalPayService payService) throws PayErrorException {
        log.info("Paypal支付回调处理开始===================={}", JSON.toJSONString(payMessage.getPayMessage()));
        log.info("Paypal支付回调处理开始===================={}", context);
        //交易状态
        if ("SUCCESS".equals(payMessage.getPayMessage().get("result_code"))) {
            /////这里进行成功的处理
            sysPayOrderService.callback(payMessage.getOutTradeNo(),null);
            return payService.getPayOutMessage("SUCCESS", "OK");
        }
        return payService.getPayOutMessage("fail", "失败");
    }

}