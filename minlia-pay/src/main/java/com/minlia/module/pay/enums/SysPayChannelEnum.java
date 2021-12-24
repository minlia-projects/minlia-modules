package com.minlia.module.pay.enums;

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
    WECHAT("wxPay"),

    /**
     * 支付宝
     */
    ALIPAY("aliPay"),

    /**
     * PAYPAL
     */
    PAYPAL("paypalPay");

    private String name;

}