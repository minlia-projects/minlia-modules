package com.minlia.module.captcha.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.captcha.bean.CaptchaCro;
import com.minlia.module.captcha.entity.CaptchaEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.i18n.enums.LocaleEnum;

import java.util.Map;

/**
 * <p>
 * 验证码 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
public interface CaptchaService extends IService<CaptchaEntity> {

    CaptchaEntity send(CaptchaCro cro);

    CaptchaEntity sendByCellphone(String cellphone);

    CaptchaEntity sendByCellphone(String cellphone, String templateCode);

    CaptchaEntity sendByCellphone(String cellphone, String templateCode, Map<String, Object> variables);

    CaptchaEntity sendByCellphone(String cellphone, String templateCode, Map<String, Object> variables, LocaleEnum locale);

    CaptchaEntity sendByEmail(String email);

    CaptchaEntity sendByEmail(String email, String templateCode);

    CaptchaEntity sendByEmail(String email, String templateCode, Map<String, Object> variables);

    Response validity(String sendTo, String code);

    Response validity(CaptchaEntity entity, String code);

}
