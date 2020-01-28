package com.minlia.module.unified.payment.enumeration;

import lombok.Getter;

@Getter
public enum PayPlatformEnum {
    ALIPAY("alipay", "支付宝"),
    WECHAT("wechat", "微信");

    private String code;
    private String name;

    PayPlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
