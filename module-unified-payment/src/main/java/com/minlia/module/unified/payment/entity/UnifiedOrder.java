package com.minlia.module.unified.payment.entity;


import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnifiedOrder extends AbstractEntity {

    /**
     * 第三方订单号   微信、支付宝订单号
     */
    private String tradeNo;

    /**
     * 商户订单号    内部订单号
     */
    private String outTradeNo;

    /**
     * 通道
     */
    private PayChannelEnum channel;

    /**
     * 操作
     */
    private PayOperationEnum operation;

    /**
     * 订单金额(分)
     */
    private Integer amount;

    /**
     * 支付的内容 描述性说明文字
     */
    private String body;

}