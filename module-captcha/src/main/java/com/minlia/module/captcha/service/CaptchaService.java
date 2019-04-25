package com.minlia.module.captcha.service;


import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.ro.CaptchaCRO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证码服务
 * 持久化一个新的security Code
 * validityByCellphone 校验当前验证码是否有效
 */
@Transactional
public interface CaptchaService {

    /**
     * 校验验证码
     * @param cellphone
     * @param code
     * @return
     */
    void validityByCellphone(String cellphone, String code);

    void validityByEmail(String email, String code);

    /**
     * 发送验证码
     * @param cro
     * @return
     */
    Captcha send(CaptchaCRO cro);

    /**
     * 发送手机验证码
     * TODO: 安全性问题, 一分钟只能发送一次验证码, 需要等到时间后才能发送第二次
     * @param cellphone
     * @return
     */
    Captcha sendByCellphone(String cellphone);

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    Captcha sendByEmail(String email);

}
