package com.minlia.modules.security.authentication.credential;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 只能用其中之一来登录
 * 如: username password
 *
 * 当有多个同时传入时报出错误
 *
 */
public class LoginCredentials  implements WithUsernameCredential, WithEmailCredential, WithCellphoneCredential {

    @JsonIgnore
    private String guid;

    private String username;
    private String email;
    private String cellphone;

    private String password;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
