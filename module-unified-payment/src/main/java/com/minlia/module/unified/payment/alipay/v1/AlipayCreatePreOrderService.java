package com.minlia.module.unified.payment.alipay.v1;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.unified.payment.CreatePreOrderService;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;
import com.minlia.module.unified.payment.util.OrderNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付宝创建预订单服务
 */
@Slf4j
public class AlipayCreatePreOrderService implements CreatePreOrderService {

    /**
     * 交易通道
     * 前端提供金额
     * 购买主题(产品标题)
     * 备注
     * 后端发起订单创建流程
     */
    public StatefulBody createPreOrder(CreatePreOrderRequestBody body) {
        String result = null;
        String number = "";
        Double amount = ((Double.parseDouble(body.getAmount().toString())) / 100);

        if (StringUtils.isEmpty(body.getNumber())) {
            number = OrderNumberUtil.generateOrderNumberTimestamp(DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX);
        } else {
            number = body.getNumber();
        }

        if (StringUtils.isNotEmpty(body.getTradeType())) {
            if (body.getTradeType().equals("NATIVE")) {
                result = alipayTradePrecreatePay(body,number,amount,result);
            } else {
                result = alipayTradeAppPayPay(body,number,amount,result);
            }
        } else {
            result = alipayTradeAppPayPay(body,number,amount,result);
        }
        return SuccessResponseBody.builder().payload(result).build();
    }


    private String alipayTradePrecreatePay(CreatePreOrderRequestBody body,String number,Double amount,String result) {
        //页面端支付

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(number);
        //固定值
        model.setTotalAmount(amount.toString());
        model.setSubject(body.getSubject());
        model.setBody(body.getBody());
        model.setTimeoutExpress("30m");
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.getCallback());
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            //得到SDK的返回结果
            result = response.getBody();
            log.debug("For frontend {}",result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ApiPreconditions.is(true, ApiCode.BASED_ON,e.getMessage());
        }

        return result;
    }

    private String alipayTradeAppPayPay(CreatePreOrderRequestBody body,String number,Double amount,String result) {
        //APP支付
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
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //得到SDK的返回结果
            result = response.getBody();
            log.debug("For frontend {}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //在构造里面初始化
    private AlipayClient alipayClient;

    private AlipayConfig alipayConfig;

    public AlipayCreatePreOrderService() {
        this(null);
    }

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

            if (StringUtils.isEmpty(config.getCertificate().getAppPublicKey())) {
                throw new RuntimeException("请提供APP公钥");
            }
            if (StringUtils.isEmpty(config.getCertificate().getPlatformPublicKey())) {
                throw new RuntimeException("请提供平台公钥");
            }

            this.alipayConfig = config;

        }

        //校验通过后开始初始化
        //初始化官方SDK
        alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY,
                config.getAppId(),
                config.getCertificate().getAppPrivateKey(),AlipayConstants.FORMAT_JSON,
                AlipayConstants.CHARSET_UTF8,
                config.getCertificate().getPlatformPublicKey(),AlipayConstants.SIGN_TYPE_RSA2);
    }


    @Override
    public String getName() {
        return "alipay";
    }


    public static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

}