package com.minlia.module.unified.payment;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.unified.payment.body.CreatePreOrderRequestBody;

/**
 * 创建预订单接口
 */
public interface CreatePreOrderService {

    public static final String DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX = "AO";
    public static final String DEFAULT_WECHAT_ORDER_NUMBER_PREFIX = "WO";

    /**
     * 根据网关类型确定交易所使用的服务
     */
    public StatefulBody createPreOrder(CreatePreOrderRequestBody body);

    /**
     * 当前交易通道的名称
     */
    public String getName();

}