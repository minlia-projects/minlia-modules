package com.minlia.module.captcha.enumeration;
/**
 * Created by will on 6/19/17.
 * 发送验证码的类型
 */
public enum SecurityCodeTypeEnum {

    /**
     * 注册
     */
    USER_REGISTRATION,

    /**
     * 登陆确认
     */
    LOGIN_CONFIRM,

    /**
     * 重置密码
     */
    RESET_PASSWORD,

    /**
     * 修改密码
     */
    CHANGE_PASSWORD,

    /**
     * 信息变更
     */
    INFO_CHANGE,

    /**
     * 身份验证
     */
    SECURITY_CODE

}