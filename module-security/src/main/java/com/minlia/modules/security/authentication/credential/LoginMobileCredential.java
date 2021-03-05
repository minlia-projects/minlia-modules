package com.minlia.modules.security.authentication.credential;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginMobileCredential {

    private Integer areaCode;

    @NotBlank
    @Cellphone
    private String mobile;

    @Password
    private String password;

    @Size(max = 10)
    private String captcha;

}