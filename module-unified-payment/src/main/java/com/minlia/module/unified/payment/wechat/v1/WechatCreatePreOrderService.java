package com.minlia.module.unified.payment.wechat.v1;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceApacheHttpImpl;
import com.minlia.cloud.body.Response;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.unified.payment.CreatePreOrderService;
import com.minlia.module.unified.payment.bean.CreatePreOrderRequest;
import com.minlia.module.unified.payment.entity.UnifiedOrder;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayOperationEnum;
import com.minlia.module.unified.payment.service.UnifiedOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信创建预订单服务
 */
@Slf4j
@Service
public class WechatCreatePreOrderService implements CreatePreOrderService {

    private WxPayService wxPayService;

    @Autowired
    private UnifiedOrderService unifiedOrderService;

    @Override
    public String getName() {
        return "wechat";
    }

    /**
     * 带参数的构造方法
     */
    public WechatCreatePreOrderService(WechatConfig wechatConfig) {
        if (null == wechatConfig) {
            throw new RuntimeException("请提供交易参数配置");
        } else {
            if (StringUtils.isEmpty(wechatConfig.getCallback())) {
                throw new RuntimeException("请提供回调地址");
            }
            if (StringUtils.isEmpty(wechatConfig.getAppId())) {
                throw new RuntimeException("请提供应用编号appId");
            }
            if (StringUtils.isEmpty(wechatConfig.getMchId())) {
                throw new RuntimeException("请提供商户编号mchId");
            }
            if (StringUtils.isEmpty(wechatConfig.getKey())) {
                throw new RuntimeException("请提供商户密钥key");
            }
        }
        WxPayService wxPayService = new WxPayServiceApacheHttpImpl();
        WxPayConfig config = new WxPayConfig();
        config.setAppId(wechatConfig.getAppId());
        config.setMchId(wechatConfig.getMchId());
        config.setMchKey(wechatConfig.getKey());
        config.setNotifyUrl(wechatConfig.getCallback());
        wxPayService.setConfig(config);
        this.wxPayService = wxPayService;
    }

    @Override
    public Response createPreOrder(CreatePreOrderRequest body) {
        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                .outTradeNo(StringUtils.isEmpty(body.getNumber()) ? NumberGenerator.generatorByYMDHMSS(DEFAULT_WECHAT_ORDER_NUMBER_PREFIX, 1) : body.getNumber())
                .tradeType(body.getTradeType())
                .totalFee(Integer.parseInt(body.getAmount().toString()))
                .notifyUrl(wxPayService.getConfig().getNotifyUrl())
                .openid(body.getOpenid())
                .body(body.getBody())
                .attach(body.getAttach())
                .spbillCreateIp(RequestIpUtils.getClientIP())
                .build();

        if (null != body.getTradeType()) {
            request.setTradeType(body.getTradeType());
            if (body.getTradeType().equals("NATIVE")) {
                request.setProductId(body.getProductId());
            }
        } else {
            request.setTradeType("APP");
        }

        try {
            Map result = wxPayService.getPayInfo(request);
            log.info("微信第三方返回参数：{}", request);
            return Response.success(result);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return Response.failure();
    }

    @Override
    public Response createOrder(Object o) {
        return this.createOrder(o, PayOperationEnum.PAY);
    }

    @Override
    public Response createOrder(Object o, PayOperationEnum operation) {
        WxPayUnifiedOrderRequest request = (WxPayUnifiedOrderRequest) o;
        request.setOutTradeNo(StringUtils.isEmpty(request.getOutTradeNo()) ? NumberGenerator.generatorByYMDHMSS(DEFAULT_WECHAT_ORDER_NUMBER_PREFIX, 1) : request.getOutTradeNo());
        request.setNotifyUrl(wxPayService.getConfig().getNotifyUrl());
        request.setSpbillCreateIp(RequestIpUtils.getClientIP());
        try {
            Object result = wxPayService.createOrder(request);

            UnifiedOrder unifiedOrder = UnifiedOrder.builder()
                    .channel(PayChannelEnum.wechat)
                    .operation(operation)
                    .outTradeNo(request.getOutTradeNo())
                    .amount(request.getTotalFee())
                    .body(request.getBody())
                    .build();
            unifiedOrderService.create(unifiedOrder);
//            return Response.success(Optional.ofNullable(result));
            return Response.success(result);
        } catch (WxPayException e) {
            e.printStackTrace();
            return Response.failure(e.getResultCode(), StringUtils.isEmpty(e.getReturnMsg()) ? e.getCustomErrorMsg() : e.getReturnMsg());
        }
    }

}