package com.minlia.module.unified.payment.alipay.v1;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.unified.payment.CreatePreOrderService;
import com.minlia.module.unified.payment.bean.CreatePreOrderRequest;
import com.minlia.module.unified.payment.entity.UnifiedOrder;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayOperationEnum;
import com.minlia.module.unified.payment.enumeration.PayPlatformEnum;
import com.minlia.module.unified.payment.service.UnifiedOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付宝创建预订单服务
 */
@Slf4j
@Service
public class AlipayCreatePreOrderService implements CreatePreOrderService {

    @Autowired
    private UnifiedOrderService unifiedOrderService;

    /**
     * 交易通道
     * 前端提供金额
     * 购买主题(产品标题)
     * 备注
     * 后端发起订单创建流程
     */
    public Response createPreOrder(CreatePreOrderRequest createPreOrderRequest) {
        String number;
        Double amount = ((Double.parseDouble(createPreOrderRequest.getAmount().toString())) / 100);

        if (StringUtils.isEmpty(createPreOrderRequest.getNumber())) {
            number = NumberGenerator.generatorByYMDHMSS(DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX, 1);
        } else {
            number = createPreOrderRequest.getNumber();
        }

        AlipayResponse result = null;
        switch (createPreOrderRequest.getChannel()) {
            case ALIPAY_FACE:
                result = precreatePay(createPreOrderRequest, number, amount);
                break;
            case ALIPAY_WAP:
                result = wapPay(createPreOrderRequest, number, amount);
                break;
            case ALIPAY_APP:
                result = appPay(createPreOrderRequest, number, amount);
                break;
            case ALIPAY_PC:
                result = pagePay(createPreOrderRequest, number, amount);
                break;
        }

        log.info("支付宝第三方返回参数：{}", result);
        return Response.success(result);
    }

    @Override
    public Response createOrder(Object o) {
        return this.createOrder(o, PayOperationEnum.PAY);
    }

    @Override
    public Response createOrder(Object obj, PayOperationEnum operation) {
        CreatePreOrderRequest request = (CreatePreOrderRequest) obj;
        Response response = createPreOrder(request);

        UnifiedOrder unifiedOrder = UnifiedOrder.builder()
                .channel(((CreatePreOrderRequest) obj).getChannel())
                .operation(operation)
                .outTradeNo(request.getNumber())
                .amount(request.getAmount())
                .body(request.getBody())
                .build();
        unifiedOrderService.create(unifiedOrder);
        return response;
    }

    /**
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * alipay.trade.precreate(统一收单线下交易预创建)
     * https://docs.open.alipay.com/api_1/alipay.trade.precreate/
     *
     * @param body
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    private AlipayTradePrecreateResponse precreatePay(CreatePreOrderRequest body, String outTradeNo, Double totalAmount) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount.toString());
        model.setSubject(body.getSubject());
        model.setBody(body.getBody());
        model.setTimeoutExpress("30m");
        model.setProductCode("FACE_TO_FACE_PAYMENT");
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getCallback());
        try {
            return alipayClient.execute(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            ApiAssert.state(false, SystemCode.Exception.INTERNAL_SERVER_ERROR.code(), e.getMessage());
            return null;
        }
    }

    /**
     * 外部商户创建订单并支付
     * alipay.trade.wap.pay(手机网站支付接口2.0)\
     * https://docs.open.alipay.com/api_1/alipay.trade.wap.pay/
     *
     * @param body
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    private AlipayTradeWapPayResponse wapPay(CreatePreOrderRequest body, String outTradeNo, Double totalAmount) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(StringUtils.isNotBlank(body.getReturnUrl()) ? body.getReturnUrl() : alipayConfig.getReturnUrl());
        request.setNotifyUrl(alipayConfig.getCallback());
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount.toString());
        model.setSubject(body.getSubject());
        model.setBody(body.getBody());
        model.setTimeoutExpress("120m");
        model.setProductCode("QUICK_WAP_WAY");
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getCallback());
        try {
            return alipayClient.pageExecute(request, "get");
        } catch (Exception e) {
            log.error(e.getMessage());
            ApiAssert.state(false, SystemCode.Exception.INTERNAL_SERVER_ERROR.code(), e.getMessage());
            return null;
        }
    }

    /**
     * PC场景下单并支付
     * alipay.trade.page.pay(统一收单下单并支付页面接口)
     * https://docs.open.alipay.com/api_1/alipay.trade.page.pay/
     *
     * @param body
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    public AlipayTradePagePayResponse pagePay(CreatePreOrderRequest body, String outTradeNo, Double totalAmount) {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(StringUtils.isNotBlank(body.getReturnUrl()) ? body.getReturnUrl() : alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getCallback());
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(body.getSubject());
        model.setBody(body.getBody());
        model.setTotalAmount(totalAmount.toString());
        model.setTimeoutExpress("120m");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //自定义参数请转码后传入该参数内，可以传值逗号，等号之类，但不能传引号，否则会报错，其他特殊符号是否报错以实测为准
//        String passback_params = "{ab=测试一下;tdst=公共参数;ccsd=gds；dfa=23·12}";
//        String passback_params2 = URLEncoder.encode(passback_params, "UTF-8");
//        model.setPassbackParams(passback_params2);
        alipayRequest.setBizModel(model);
        //get请求方式，返回http链接
        AlipayTradePagePayResponse respose = null;
        try {
            respose = alipayClient.pageExecute(alipayRequest, "get");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //post请求方式，返回from表单
//        AlipayTradePagePayResponse respose1= alipayClient.pageExecute(alipayRequest);
        return respose;
    }

    /**
     * 外部商户APP唤起快捷SDK创建订单并支付
     * alipay.trade.app.pay(app支付接口2.0)
     * https://docs.open.alipay.com/api_1/alipay.trade.app.pay/
     *
     * @param body
     * @param number
     * @param amount
     * @return
     */
    private AlipayTradeAppPayResponse appPay(CreatePreOrderRequest body, String number, Double amount) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body.getBody());
        model.setSubject(body.getSubject());
        model.setOutTradeNo(number);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(amount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getCallback());
        try {
            return alipayClient.sdkExecute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //在构造里面初始化
    private AlipayClient alipayClient;

    private AlipayConfig alipayConfig;

    public AlipayCreatePreOrderService(AlipayConfig config) {
        if (null == config) {
            throw new RuntimeException("请提供交易参数配置");
        } else {
            if (StringUtils.isEmpty(config.getAppId())) {
                throw new RuntimeException("请提供AppId");
            }
            if (StringUtils.isEmpty(config.getCallback())) {
                throw new RuntimeException("请提供回调地址");
            }
            if (null == config.getCertificate()) {
                throw new RuntimeException("请提供证书配置信息");
            }
            if (StringUtils.isEmpty(config.getCertificate().getAppPrivateKey())) {
                throw new RuntimeException("请提供APP私钥");
            }
//            if (StringUtils.isEmpty(config.getCertificate().getAppPublicKey())) {
//                throw new RuntimeException("请提供APP公钥");
//            }
            if (StringUtils.isEmpty(config.getCertificate().getPlatformPublicKey())) {
                throw new RuntimeException("请提供平台公钥");
            }
            this.alipayConfig = config;
        }

        alipayClient = new DefaultAlipayClient(
                ALIPAY_GATEWAY,
                config.getAppId(),
                config.getCertificate().getAppPrivateKey(),
                AlipayConstants.FORMAT_JSON,
                AlipayConstants.CHARSET_UTF8,
                config.getCertificate().getPlatformPublicKey(),
                AlipayConstants.SIGN_TYPE_RSA2);
    }

    @Override
    public String getName() {
        return "alipay";
    }

    public static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

}