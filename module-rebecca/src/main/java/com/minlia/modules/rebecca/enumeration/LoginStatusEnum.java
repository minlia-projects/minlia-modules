package com.minlia.modules.rebecca.enumeration;

public enum  LoginStatusEnum {

    /**
     * 登录成功
     */
    SUCCESS,

    /**
     * 账号已失效
     */
    ACCOUNT_EXPIRED,

    /**
     * 账号被锁定
     */
    ACCOUNT_LOCKED,

    /**
     * 账号不可用
     */
    ACCOUNT_ENABLED,

    /**
     * 凭证已失效：密码已失效
     */
    CREDENTIALS_EXPIRED,

    /*
     * 密码无效
     */
    PASSWORD_INVALID;

}