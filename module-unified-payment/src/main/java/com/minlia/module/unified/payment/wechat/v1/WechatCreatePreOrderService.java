package com.minlia.module.unified.payment.wechat.v1;

import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
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

    private WxPayConfig wxPayConfig;

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
        this.wxPayConfig = config;
    }

    @Override
    public Response createPreOrder(CreatePreOrderRequest createPreOrderRequest) {
        return createOrder(createPreOrderRequest);
    }

    @Override
    public Response createOrder(Object obj) {
        return this.createOrder(obj, PayOperationEnum.PAY);
    }

    @Override
    public Response createOrder(Object obj, PayOperationEnum operation) {
        CreatePreOrderRequest createPreOrderRequest = (CreatePreOrderRequest) obj;
        String outTradeNo = StringUtils.isEmpty(createPreOrderRequest.getNumber()) ? NumberGenerator.generatorByYMDHMSS(DEFAULT_WECHAT_ORDER_NUMBER_PREFIX, 1) : createPreOrderRequest.getNumber();
        Integer totalFee = Integer.parseInt(createPreOrderRequest.getAmount().toString());
        Object result = null;
        if (PayChannelEnum.WXPAY_MICROPAY.equals(createPreOrderRequest.getChannel())) {
            WxPayMicropayRequest micropayRequest = WxPayMicropayRequest.newBuilder()
                    .outTradeNo(outTradeNo)
                    .totalFee(totalFee)
                    .body(createPreOrderRequest.getBody())
                    .attach(createPreOrderRequest.getAttach())
                    .spbillCreateIp(RequestIpUtils.getClientIP())
                    .authCode("")
                    .build();
            try {
                result = wxPayService.micropay(micropayRequest);
            } catch (WxPayException e) {
                e.printStackTrace();
                return Response.failure(e.getResultCode(), StringUtils.isNotEmpty(e.getReturnMsg()) ? e.getReturnMsg() : e.getCustomErrorMsg());
            }
        } else {
            WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                    .outTradeNo(outTradeNo)
                    .tradeType(createPreOrderRequest.getChannel().getCode())
                    .totalFee(totalFee)
                    .notifyUrl(wxPayService.getConfig().getNotifyUrl())
                    .openid(createPreOrderRequest.getOpenid())
                    .body(createPreOrderRequest.getBody())
                    .attach(createPreOrderRequest.getAttach())
                    .spbillCreateIp(RequestIpUtils.getClientIP())
                    .productId(wxPayConfig.getAppId())
                    .build();
            try {
                result = wxPayService.createOrder(request);
            } catch (WxPayException e) {
                e.printStackTrace();
                return Response.failure(e.getResultCode(), StringUtils.isNotEmpty(e.getReturnMsg()) ? e.getReturnMsg() : e.getCustomErrorMsg());
            }
        }

        UnifiedOrder unifiedOrder = UnifiedOrder.builder()
                .channel(createPreOrderRequest.getChannel())
                .operation(operation)
                .outTradeNo(outTradeNo)
                .amount(totalFee)
                .body(createPreOrderRequest.getBody())
                .build();
        unifiedOrderService.create(unifiedOrder);
        return Response.success(result);
    }

}