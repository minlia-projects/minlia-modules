package com.minlia.module.unified.payment;

import com.minlia.cloud.body.Response;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;

/**
 * 创建预订单接口
 */
public interface CreatePreOrderService {

    String DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX = "AO";
    String DEFAULT_WECHAT_ORDER_NUMBER_PREFIX = "WO";

    /**
     * 根据网关类型确定交易所使用的服务
     */
    Response createPreOrder(CreatePreOrderRequestBody body);

    /**
     * 当前交易通道的名称
     */
    String getName();

}