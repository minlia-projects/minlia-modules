package com.minlia.module.pay.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付通道
 */
@Getter
@AllArgsConstructor
public enum SysPayChannelEnum {

    /**
     * 微信
     */
    WECHAT(0, "wxPay"),

    /**
     * 支付宝
     */
    ALIPAY(1, "aliPay"),

    /**
     * PAYPAL
     */
    PAYPAL(2, "paypalPay");

    private final Integer value;

    @EnumValue
    private final String code;

}