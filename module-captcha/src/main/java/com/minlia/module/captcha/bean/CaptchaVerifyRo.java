package com.minlia.module.captcha.bean;

import com.minlia.module.captcha.enumeration.CaptchaTypeEnum;
import com.minlia.module.common.validation.Cellphone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
public class CaptchaVerifyRo {

    @NotNull
    private CaptchaTypeEnum type;

    @Email
    private String email;

    @Cellphone
    private String cellphone;

    @NotBlank
    @Size(min = 4, max = 8)
    private String vcode;

}