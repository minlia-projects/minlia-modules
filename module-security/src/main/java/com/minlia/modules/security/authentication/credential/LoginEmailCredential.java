package com.minlia.modules.security.authentication.credential;

import com.minlia.module.common.validation.Password;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginEmailCredential {

    @NotBlank
    @Email
    private String email;

    @Password
    private String password;

    @Size(max = 10)
    private String captcha;

}