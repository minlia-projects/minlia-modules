package com.minlia.module.captcha.ro;

import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.common.validation.Cellphone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CaptchaVerifyRO {

    @NotNull
    private CaptchaMethodEnum method;

    @Email
    private String email;

    @Cellphone
    private String cellphone;

    @NotBlank
    @Size(min = 4, max = 8)
    private String code;

}