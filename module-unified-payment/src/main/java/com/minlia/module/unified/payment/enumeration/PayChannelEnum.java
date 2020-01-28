package com.minlia.module.unified.payment.enumeration;

import lombok.Getter;

@Getter
public enum PayChannelEnum {

    ALIPAY_FACE("alipay_face", PayPlatformEnum.ALIPAY, "支付宝app"),
    ALIPAY_APP("alipay_app", PayPlatformEnum.ALIPAY, "支付宝app"),
    ALIPAY_PC("alipay_pc", PayPlatformEnum.ALIPAY, "支付宝pc"),
    ALIPAY_WAP("alipay_wap", PayPlatformEnum.ALIPAY, "支付宝wap"),
    WXPAY_MP("JSAPI", PayPlatformEnum.WECHAT, "微信公众账号支付"),
    WXPAY_MWEB("MWEB", PayPlatformEnum.WECHAT, "微信H5支付"),
    WXPAY_NATIVE("NATIVE", PayPlatformEnum.WECHAT, "微信Native支付"),
    WXPAY_MINI("JSAPI", PayPlatformEnum.WECHAT, "微信小程序支付"),
    WXPAY_APP("APP", PayPlatformEnum.WECHAT, "微信APP支付"),
    WXPAY_MICROPAY("MICROPAY", PayPlatformEnum.WECHAT, "微信付款码支付");

    private String code;

    private PayPlatformEnum platform;

    private String desc;

    PayChannelEnum(String code, PayPlatformEnum platform, String desc) {
        this.code = code;
        this.platform = platform;
        this.desc = desc;
    }

}