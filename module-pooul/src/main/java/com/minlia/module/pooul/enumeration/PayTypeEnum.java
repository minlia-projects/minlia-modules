package com.minlia.module.pooul.enumeration;

/**
 * 支付类型，不同的支付类型，pay_type值不一样
 * Created by garen on 2017/11/18.
 */
public enum PayTypeEnum {

    /**
     * 统一反扫支付
     */
    common_micro("common.micro"),

    /**
     * 微信扫码支付
     */
    wechat_scan("wechat.scan"),

    /*
     * 微信公众号支付-原生
     */
    wechat_jsapi("wechat.jsapi"),

    /**
     * 微信WAP支付
     */
    wechat_wap("wechat.wap"),

    /**
     * 微信APP支付
     */
    wechat_app("wechat.app"),

    /**
     * 微信小程序支付
     */
    wechat_jsminipg("wechat.jsminipg"),

    /**
     * 支付宝正扫
     */
    alipay_scan("alipay.scan"),

    /**
     * 支付宝服务窗支付
     */
    alipay_jsapi("alipay.jsapi"),

    /**
     * 支付宝WAP支付
     */
    alipay_wap("alipay.wap"),

    /**
     * 支付宝APP支付
     */
    alipay_app("alipay.app");

    private String name;

    public String getName() {
        return name;
    }

    PayTypeEnum(String name) {
        this.name = name;
    }

}
