package com.minlia.module.pay.handler;

import com.alibaba.fastjson.JSON;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.v3.api.WxPayService;
import com.egzosn.pay.wx.v3.bean.response.WxPayMessage;
import com.minlia.module.pay.service.SysPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信支付回调处理器
 * Created by ZaoSheng on 2016/6/1.
 */
@Slf4j
@Component
public class WxPayMessageHandler implements PayMessageHandler<WxPayMessage, WxPayService> {

    @Lazy
    @Autowired
    private SysPayOrderService sysPayOrderService;

    @Override
    public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, WxPayService payService) throws PayErrorException {
        log.info("微信支付回调处理开始===================={}", JSON.toJSONString(payMessage));
        //交易状态
        if ("SUCCESS".equals(payMessage.getPayMessage().get("result_code"))) {
            sysPayOrderService.callback(payMessage.getOutTradeNo(), payMessage.getTransactionId());
            return payService.getPayOutMessage("SUCCESS", "OK");
        }
        return payService.getPayOutMessage("FAIL", "失败");
    }

}