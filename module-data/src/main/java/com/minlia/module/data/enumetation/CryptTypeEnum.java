package com.minlia.module.data.enumetation;

/**
 * 验证类型枚举
 *
 * @author wangzhuhua
 * @date 2018/09/04 下午5:20
 **/
public enum CryptTypeEnum {
    /**
     * AES加密（这个可是加密，不是脱敏）
     */
    AES,

    /**
     * 手机号
     */
    @Deprecated
    PHONE_NUMBER,

    /**
     * 身份证
     */
    @Deprecated
    ID_CARD,

    /**
     * 银行卡
     */
    @Deprecated
    BANK_CARD
}