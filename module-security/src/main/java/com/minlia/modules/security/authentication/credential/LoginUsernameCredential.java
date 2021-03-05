package com.minlia.modules.security.authentication.credential;

import com.minlia.module.common.validation.Password;
import com.minlia.module.common.validation.Username;
import lombok.Data;

@Data
public class LoginUsernameCredential {

    @Username
    private String username;

    @Password
    private String password;

}