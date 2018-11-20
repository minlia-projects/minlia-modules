package com.minlia.module.unified.payment;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.marshall.JsonHelper;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;
import com.minlia.module.unified.payment.util.OrderNumberUtil;
import org.junit.Test;

/**
 * Created by will on 9/14/17.
 */
public class CreatePreOrderServiceTest extends AbstractServiceTest {

    /**
     * 配置信息参见父类
     */
    @Test
    public void alipayCreatePreOrder() {
        CreatePreOrderRequestBody body = new CreatePreOrderRequestBody();
        String number = OrderNumberUtil.generateOrderNumberTimestamp(CreatePreOrderService.DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX);

        //设置交易参数
        body.setAmount(1);
        body.setSubject("支付主题");
        body.setBody("备注内容");

        body.setNumber(number);

        Response response = alipayCreatePreOrderService().createPreOrder(body);
        String res = JsonHelper.serialize(response);
        System.out.println(res);
    }

    /**
     * 配置信息参见父类
     */
    @Test
    public void wechatCreatePreOrder() {
        CreatePreOrderRequestBody body = new CreatePreOrderRequestBody();
        String number = OrderNumberUtil.generateOrderNumberTimestamp(CreatePreOrderService.DEFAULT_WECHAT_ORDER_NUMBER_PREFIX);

        //设置交易数据
        body.setAmount(1);
        body.setSubject("WECHAT支付主题");
        body.setBody("WECHAT备注内容");

        body.setNumber(number);

        Response response = wechatCreatePreOrderService().createPreOrder(body);

        Object result = response.getPayload();
        System.out.println(result);

        String res = JsonHelper.serialize(response);
        System.out.println(res);
        System.out.println(response.getPayload());
    }

}
