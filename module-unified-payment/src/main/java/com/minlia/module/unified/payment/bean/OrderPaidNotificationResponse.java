package com.minlia.module.unified.payment.bean;

import com.minlia.cloud.body.Body;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import lombok.Data;

/**
 * Created by will on 9/15/17.
 * 定义本系统的通知体
 */
@Data
public class OrderPaidNotificationResponse implements Body {

    /**
     * 第三方交易号
     */
    private String gatewayTradeNo;//transaction_id  tradeNo

    /**
     * 商户订单号
     */
    private String merchantTradeNo;//out_trade_no outTradeNo

    /**
     * 支付渠道
     */
    private PayChannelEnum channel;

    /**
     * 付款人
     */
    private String paidBy;//openid buyerId

    /**
     * 付款金额
     */
    private Integer amount;//total_fee receiptAmount

    private String subject;//付款主题, 为买什么而支付

    private String body;//付款备注  attach ro

    private String sign;//sign

}
