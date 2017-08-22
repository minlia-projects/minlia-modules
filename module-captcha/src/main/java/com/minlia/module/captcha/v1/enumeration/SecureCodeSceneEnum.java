package com.minlia.module.captcha.v1.enumeration;

/**
 * Created by will on 6/19/17.
 * 验证码使用的场景
 */
public enum SecureCodeSceneEnum {
    /**
     * 注册
     */
    USER_REGISTRATION,

    /**
     * 重置密码
     */
    RESET_PASSWORD,

    /**
     * 修改密码
     */
    CHANGE_PASSWORD,
    /**
     * 绑定或注册
     */
    BIND_OR_REGISTRATION

}