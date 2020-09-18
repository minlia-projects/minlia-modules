package com.minlia.module.captcha.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/1 7:55 PM
 */
@Data
@Builder
@AllArgsConstructor
public class CaptchaSendResult {

    /**
     * 验证码内容
     */
    private String vcode;

    /**
     * 倒计时
     */
    private long countdown;

}
