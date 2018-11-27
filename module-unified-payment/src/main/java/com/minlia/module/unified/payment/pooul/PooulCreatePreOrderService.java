//package com.minlia.module.unified.payment.pooul;
//
//import com.minlia.cloud.bean.Response;
//import com.minlia.module.unified.payment.CreatePreOrderService;
//import com.minlia.module.unified.payment.bean.CreatePreOrderRequestBody;
//import com.minlia.module.unified.payment.pooul.bean.PooulWechatMpPaymentRequestBody;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//
///**
// * 微信创建预订单服务
// */
//public class PooulCreatePreOrderService implements CreatePreOrderService {
//
//    private PooulConfig pooulConfig;
//
//    @Override
//    public String getName() {
//        return "wechat";
//    }
//
//    /**
//     * 带参数的构造方法
//     */
//    public PooulCreatePreOrderService(PooulConfig pooulConfig) {
//        if (null == pooulConfig) {
//            throw new RuntimeException("请提供交易参数配置");
//        } else {
//            if (null != (pooulConfig.getCertificate())) {
//                if (!StringUtils.isEmpty(pooulConfig.getCertificate().getPlatformPublicKey())) {
//                    throw new RuntimeException("微信支付无需配置平台公钥");
//                }
//                if (!StringUtils.isEmpty(pooulConfig.getCertificate().getAppPublicKey())) {
//                    throw new RuntimeException("微信支付无需配置应用公钥");
//                }
//                if (!StringUtils.isEmpty(pooulConfig.getCertificate().getAppPrivateKey())) {
//                    throw new RuntimeException("微信支付无需配置应用私钥钥");
//                }
//            }
//            if (StringUtils.isEmpty(pooulConfig.getCallback())) {
//                throw new RuntimeException("请提供回调地址");
//            }
//            if (StringUtils.isEmpty(pooulConfig.getAppId())) {
//                throw new RuntimeException("请提供应用编号appId");
//            }
//            if (StringUtils.isEmpty(pooulConfig.getMchId())) {
//                throw new RuntimeException("请提供商户编号mchId");
//            }
//            if (StringUtils.isEmpty(pooulConfig.getKey())) {
//                throw new RuntimeException("请提供商户密钥key");
//            }
//            this.pooulConfig = pooulConfig;
//        }
//    }
//
//    /**
//     * 交易通道
//     * 前端提供金额
//     * 购买主题(产品标题)
//     * 备注
//     * 后端发起订单创建流程
//     */
//    @Override
//    public Response createPreOrder(CreatePreOrderRequestBody bean) {
//        PooulWechatMpPaymentRequestBody requestBody = new PooulWechatMpPaymentRequestBody();
//        requestBody.setSubAppid("wx469ffdb81de47e4d");
//        requestBody.setSubOpenid("oerQA5Q5clTAK8eA3tGNOAiz7s4o");
//        requestBody.setMchTradeId("MINLIA-TEST-ORDER-" + RandomStringUtils.randomNumeric(10));
//        requestBody.setTotalFee(1);
//        requestBody.setBody("测试支付");
//        requestBody.setNonceStr(RandomStringUtils.randomAlphabetic(32));
////        requestBody.setNotifyUrl("XXXX");
//
//        requestBody.setPayType("wechat.jsapi");
//
////        //设置为沙箱模式
////        SwiftpassStatefulApiResponseBody statefulApiResponseBody = swiftpassApi.wechatJspay(requestBody);
////
////        assertNotNull(statefulApiResponseBody);
////        assertTrue(statefulApiResponseBody.isSuccess());
////        assertEquals("0", statefulApiResponseBody.getCode());
////
////        SwiftpassWechatJspayPaymentResponseBody apiHttpResponseBody = (SwiftpassWechatJspayPaymentResponseBody) statefulApiResponseBody.getPayload();
////
////
////        //把pay_info中的appId 替换为wx469ffdb81de47e4d，然后用这个密钥  6Uc2ACa4EpRuZe86fetUsPEcuspUWUcr 重新计算 paySign
////        //你相当于多做一步，把pay_info的信息调整一下
////        //你调了以后可以用微信的工具校验一下https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=20_1
////        apiHttpResponseBody.setAppId("wx469ffdb81de47e4d");
////        SignatureBody signatureBody = new SignatureBody();
////        signatureBody.setSalt("6Uc2ACa4EpRuZe86fetUsPEcuspUWUcr");
////        signatureBody.setAlgorithmic(SignatureAlgorithmic.MD5);
////        signatureBody.setCaseControl(CaseControl.TO_UPPER_CASE);
////        signatureBody.setExcludeSaltParameter(false);
////        signatureBody.setSaltParameterPrefix("key=");
////        signatureBody.setRaw(apiHttpResponseBody);
////        signatureBody.setDelimiter("&");
////        SignatureBinder.bind(signatureBody);
////        String pkg = apiHttpResponseBody.getPkg();
////        String newSign = apiHttpResponseBody.getPaySign();
////
////        log.debug("最终需要的值");
////        System.out.println("appId:" + apiHttpResponseBody.getAppId());
////        System.out.println("package:" + pkg);
////        System.out.println("timeStamp:" + apiHttpResponseBody.getTimeStamp());
////        System.out.println("nonceStr:" + apiHttpResponseBody.getNonceStr());
////        System.out.println("signType:" + apiHttpResponseBody.getSignType());
////        System.out.println("paySign:" + newSign);
//
//        return Response.failure();
//    }
//
//}