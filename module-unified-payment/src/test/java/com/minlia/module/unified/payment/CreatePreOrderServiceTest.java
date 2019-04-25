//package com.minlia.module.unified.payment;
//
//import com.minlia.cloud.bean.Response;
//import com.minlia.cloud.marshall.JsonHelper;
//import com.minlia.module.unified.payment.bean.CreatePreOrderRequest;
//import com.minlia.module.unified.payment.util.OrderNumberUtil;
//import org.junit.Test;
//
///**
// * Created by will on 9/14/17.
// */
//public class CreatePreOrderServiceTest extends AbstractServiceTest {
//
//    /**
//     * 配置信息参见父类
//     */
//    @Test
//    public void alipayCreatePreOrder() {
//        CreatePreOrderRequest bean = new CreatePreOrderRequest();
//        String number = OrderNumberUtil.generateOrderNumberTimestamp(CreatePreOrderService.DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX);
//
//        //设置交易参数
//        bean.setAmount(1);
//        bean.setSubject("支付主题");
//        bean.setBody("备注内容");
//
//        bean.setNumber(number);
//
//        Response response = alipayCreatePreOrderService().createPreOrder(bean);
//        String res = JsonHelper.serialize(response);
//        System.out.println(res);
//    }
//
//    /**
//     * 配置信息参见父类
//     */
//    @Test
//    public void wechatCreatePreOrder() {
//        CreatePreOrderRequest bean = new CreatePreOrderRequest();
//        String number = OrderNumberUtil.generateOrderNumberTimestamp(CreatePreOrderService.DEFAULT_WECHAT_ORDER_NUMBER_PREFIX);
//
//        //设置交易数据
//        bean.setAmount(1);
//        bean.setSubject("WECHAT支付主题");
//        bean.setBody("WECHAT备注内容");
//
//        bean.setNumber(number);
//
//        Response response = wechatCreatePreOrderService().createPreOrder(bean);
//
//        Object result = response.getPayload();
//        System.out.println(result);
//
//        String res = JsonHelper.serialize(response);
//        System.out.println(res);
//        System.out.println(response.getPayload());
//    }
//
//}
