package com.minlia.module.captcha.v1.code;

import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.code.ApiCode;

@Localized
public class CaptchaApiCode extends ApiCode {
    public CaptchaApiCode() {
        throw new AssertionError();
    }


    /**
     * 定义验证码模块代码基数为11000
     */
    public static final Integer SECURITY_MODULE_CODE_BASEDON= BASED_ON +1000;







}
