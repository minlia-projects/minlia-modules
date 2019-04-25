package com.minlia.module.unified.payment;

import com.minlia.cloud.body.Response;
import com.minlia.module.unified.payment.bean.CreatePreOrderRequest;
import com.minlia.module.unified.payment.enumeration.PayOperationEnum;

/**
 * 创建预订单接口
 */
public interface CreatePreOrderService <T> {

    String DEFAULT_ALIPAY_ORDER_NUMBER_PREFIX = "AO";
    String DEFAULT_WECHAT_ORDER_NUMBER_PREFIX = "WO";

    /**
     * 根据网关类型确定交易所使用的服务
     */
    Response createPreOrder(CreatePreOrderRequest request);

    Response createOrder(T t);

    Response createOrder(T t, PayOperationEnum operation);

    /**
     * 当前交易通道的名称
     */
    String getName();

}