package com.minlia.module.pay.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 */
@Getter
@AllArgsConstructor
public enum SysPayMethodEnum {

    /**
     * 网页
     */
    PAGE(0, "PAGE"),

    /**
     * 扫码
     */
    QR(1, "QR"),

    /**
     * APP
     */
    APP(2, "APP"),

    /**
     * WAP
     */
    WAP(3, "WAP"),

    /**
     * 小程序
     */
    MINAPP(4, "MINAPP");

    private Integer value;

    @EnumValue
    private String code;

}