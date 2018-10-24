package com.minlia.module.captcha.bean.to;

import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.common.validation.Cellphone;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
public class CaptchaTO {

    @NotNull
    private CaptchaMethodEnum method;

    @Email
    private String email;

    @Cellphone
    private String cellphone;

}
