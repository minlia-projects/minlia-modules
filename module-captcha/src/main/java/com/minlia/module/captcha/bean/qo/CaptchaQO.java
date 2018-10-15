package com.minlia.module.captcha.bean.qo;

import lombok.Data;

/**
 * Created by garen on 2018/1/23.
 */
@Data
public class CaptchaQO {

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
     * 是否锁定
     */
    private Boolean locked;

}
