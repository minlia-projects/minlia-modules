package com.minlia.modules.security.authentication.credential;


import lombok.Data;

/**
 * 只能用其中之一来登录
 * 如: username password
 *
 * 当有多个同时传入时报出错误
 *
 */
@Data
public class LoginCredentials  implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {

    private String username;

    private String cellphone;

    private String email;

    private String password;

    private String currrole;

}
