package com.minlia.module.unified.payment.wechat.v1;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceApacheHttpImpl;
import com.minlia.cloud.body.Response;
import com.minlia.module.unified.payment.CreatePreOrderService;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;
import com.minlia.module.unified.payment.util.OrderNumberUtil;
import com.minlia.module.unified.payment.util.RequestIpUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 微信创建预订单服务
 */
public class WechatCreatePreOrderService implements CreatePreOrderService {

    private WechatConfig wechatConfig;

    /**
     * 无参构造
     */
    public WechatCreatePreOrderService() {
        this(null);
    }

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
            if (null != (wechatConfig.getCertificate())) {
                if (!StringUtils.isEmpty(wechatConfig.getCertificate().getPlatformPublicKey())) {
                    throw new RuntimeException("微信支付无需配置平台公钥");
                }
                if (!StringUtils.isEmpty(wechatConfig.getCertificate().getAppPublicKey())) {
                    throw new RuntimeException("微信支付无需配置应用公钥");
                }
                if (!StringUtils.isEmpty(wechatConfig.getCertificate().getAppPrivateKey())) {
                    throw new RuntimeException("微信支付无需配置应用私钥钥");
                }
            }

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

            this.wechatConfig = wechatConfig;
        }
    }

    /**
     * 交易通道
     * 前端提供金额
     * 购买主题(产品标题)
     * 备注
     * 后端发起订单创建流程
     */
    @Override
    public Response createPreOrder(CreatePreOrderRequestBody body) {
        String number = "";

        if (StringUtils.isEmpty(body.getNumber())) {
            number = OrderNumberUtil.generateOrderNumberTimestamp(DEFAULT_WECHAT_ORDER_NUMBER_PREFIX);
        } else {
            number = body.getNumber();
        }

        WxPayService wxPayService = new WxPayServiceApacheHttpImpl();
        //将本系统的交易凭证转换为WEIXIN交易参数
        WxPayConfig config = new WxPayConfig();
        config.setAppId(wechatConfig.getAppId());
        config.setMchId(wechatConfig.getMchId());
        config.setMchKey(wechatConfig.getKey());

        wxPayService.setConfig(config);
        try {
            WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                    .notifyUrl(wechatConfig.getCallback())
                    .build();
            request.setBody(body.getBody());
            request.setTotalFee(Integer.parseInt(body.getAmount().toString()));
            request.setAttach(body.getSubject());
            request.setSpbillCreateIp(RequestIpUtils.getIpAddr());
            request.setOutTradeNo(number);


            if (null != body.getTradeType()) {
                request.setTradeType(body.getTradeType());
                if (body.getTradeType().equals("NATIVE")) {
                    request.setProductId(body.getProductId());
                }
            } else {
                request.setTradeType("APP");
            }

            Map result = wxPayService.getPayInfo(request);
            return Response.success(result);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return Response.failure();
    }

}