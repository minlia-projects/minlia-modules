package com.minlia.module.captcha.body;

import lombok.Data;

/**
 * Created by garen on 2018/1/23.
 */
@Data
public class CaptchaQueryRequestBody {

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 验证码内容
     */
    private String code;

    /**
     * 是否已被使用
     */
    private Boolean used = false;

    /**
     * 是否锁定(用于风控) TODO
     */
    private Boolean locked;

}
