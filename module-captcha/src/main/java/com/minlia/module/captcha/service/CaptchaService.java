package com.minlia.module.captcha.service;


import com.minlia.module.captcha.bean.domain.Captcha;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证码服务
 * 持久化一个新的security Code
 * validity 校验当前验证码是否有效
 */
@Transactional(readOnly = false)
public interface CaptchaService {

    /**
     * 校验验证码
     * @param cellphone
     * @param code
     * @return
     */
    void validity(String cellphone, String code);

    /**
     * 发送验证码
     * TODO: 安全性问题, 一分钟只能发送一次验证码, 需要等到时间后才能发送第二次
     * @param cellphone
     * @return
     */
    Captcha send(String cellphone);

}
