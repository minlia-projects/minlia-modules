package com.minlia.modules.security.authentication.credential;


import lombok.Data;

/**
 * 只能用其中之一来登录
 * 如: username password
 * <p>
 * 当有多个同时传入时报出错误
 */
@Data
public class LoginCredentials implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 当前角色
     */
    private String currrole;

}
