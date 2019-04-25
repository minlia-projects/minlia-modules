//package com.minlia.module.unified.payment.pooul;
//
//import com.github.binarywang.wxpay.ro.notify.WxPayOrderNotifyResult;
//import com.github.binarywang.wxpay.config.WxPayConfig;
//import com.github.binarywang.wxpay.exception.WxPayException;
//import com.github.binarywang.wxpay.service.WxPayService;
//import com.github.binarywang.wxpay.service.impl.WxPayServiceApacheHttpImpl;
//import com.minlia.module.unified.payment.ro.OrderPaidNotificationResponse;
//import com.minlia.module.unified.payment.ro.PayTypeEnum;
//import com.minlia.module.unified.payment.event.OrderPaidEventProducer;
//import com.minlia.module.unified.payment.util.XmlUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by garen on 2018/4/16.
// */
//@RestController
//@RequestMapping(value = "api/open/callback/unified/payment/pooul")
//@Api(tags = "Unified Payment Callback Pooul", description = "普尔翰达支付回调")
//@Slf4j
//public class PooulCallbackReceiveEndpoint {
//
//    @Autowired
//    private PooulConfig pooulConfig;
//
//    @RequestMapping
//    @ApiOperation(value = "普尔翰达回调", notes = "普尔翰达回调", httpMethod = "POST")
//    public String process(HttpServletRequest request) {
//        String requestedXmlString = XmlUtils.parseRequst(request);
//        log.info("wechat callback here");
//        if (verifySign(requestedXmlString)) {
//            log.info("wechat sign here");
//            OrderPaidNotificationResponse ro = new OrderPaidNotificationResponse();
//            ro = mapToBody(requestedXmlString,ro);
//            log.info("返回所有参数了" + ro.toString());
//            OrderPaidEventProducer.onOrderPaid(ro);
//            return "SUCCESS";
//        } else {
//            return "FAIL";
//        }
//    }
//
//    private OrderPaidNotificationResponse mapToBody(String requestedXmlString,OrderPaidNotificationResponse ro) {
//        log.info(requestedXmlString);
//        try {
//            //这里面已经做了验证签名
//            WxPayOrderNotifyResult requestBody = getWxPayService()
//                    .parseOrderNotifyResult(requestedXmlString);
//            ro.setPaidBy(requestBody.getOpenid());
//            ro.setAmount(Integer.valueOf(requestBody.getTotalFee().toString()));
//            ro.setBody(requestBody.getAttach());
//            ro.setSubject(requestBody.getAttach());
//            ro.setGatewayTradeNo(requestBody.getTransactionId());
//            ro.setMerchantTradeNo(requestBody.getOutTradeNo());
//            ro.setPaidBy(requestBody.getOpenid());
//            ro.setSign(requestBody.getSign());
//            ro.setPayTypeEnum(PayTypeEnum.WECHAT);
//        } catch (WxPayException e) {
//            log.info("解析異常" + e.toString());
//        }
//        return ro;
//    }
//
//    /**
//     * 验证签名
//     */
//    private boolean verifySign(String requestedXmlString) {
//        if (StringUtils.isNotEmpty(requestedXmlString)) {
//            try {
//                getWxPayService().parseOrderNotifyResult(requestedXmlString);
//                //这里面已经做了验证签名, 所以直接返回真
//                return Boolean.TRUE;
//            } catch (WxPayException e) {
//                e.printStackTrace();
//                return Boolean.FALSE;
//            }
//        } else {
//            //非微信请求过来的通知, 忽略
//            return Boolean.FALSE;
//        }
//    }
//
//    /**
//     * 能够动态地从Bible里取到配置值
//     */
//    public WxPayService getWxPayService() {
//        WxPayService wxPayService = new WxPayServiceApacheHttpImpl();
//        //将本系统的交易凭证转换为WEIXIN交易参数
//        WxPayConfig config = new WxPayConfig();
//        config.setAppId(pooulConfig.getAppId());
//        config.setMchId(pooulConfig.getMchId());
//        config.setMchKey(pooulConfig.getKey());
//        wxPayService.setConfig(config);
//        return wxPayService;
//    }
//}
