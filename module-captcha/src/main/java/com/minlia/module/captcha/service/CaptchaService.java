package com.minlia.module.captcha.service;


import com.minlia.cloud.code.Code;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.ro.CaptchaQRO;
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
     *
     * @param cellphone
     * @param code
     * @return
     */
    Code validityByCellphone(String cellphone, String code);

    Code validityByEmail(String email, String code);

    Code validityByCellphone(String cellphone, String code, boolean throwsException);

    Code validityByEmail(String email, String code, boolean throwsException);

    /**
     * 发送验证码
     *
     * @param cro
     * @return
     */
    Captcha send(CaptchaCRO cro);

    /**
     * 发送手机验证码
     * TODO: 安全性问题, 一分钟只能发送一次验证码, 需要等到时间后才能发送第二次
     *
     * @param cellphone
     * @return
     */
    Captcha sendByCellphone(String cellphone);
    Captcha sendByCellphone(String cellphone, String templateCode);

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    Captcha sendByEmail(String email);
    Captcha sendByEmail(String email, String templateCode);

    Captcha queryOne(CaptchaQRO qro);

}
