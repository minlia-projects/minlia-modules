package com.minlia.modules.security.authentication.credential;

import javax.validation.constraints.NotNull;

/**
 * Created by will on 8/15/17.
 */
public class LoginCredential {

    public LoginCredential(){

    }

    public LoginCredential(String login,String password){
        this.login=login;
        this.password=password;
    }
    @NotNull
    private String login;
    @NotNull
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
