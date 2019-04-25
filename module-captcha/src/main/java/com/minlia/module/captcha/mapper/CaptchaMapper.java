package com.minlia.module.captcha.mapper;

import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.ro.CaptchaQRO;

public interface CaptchaMapper {

    void create(Captcha captcha);

    void update(Captcha captcha);

    int delete(long id);

    Captcha queryOne(CaptchaQRO qro);

    Captcha queryList(CaptchaQRO qro);

}
