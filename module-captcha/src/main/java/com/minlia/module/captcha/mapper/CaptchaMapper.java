package com.minlia.module.captcha.mapper;

import com.minlia.module.captcha.bean.qo.CaptchaQO;
import com.minlia.module.captcha.bean.domain.Captcha;
import org.springframework.stereotype.Component;

/**
 * 验证码映射接口
 * Created by garen on 2018/1/22.
 */
@Component
public interface CaptchaMapper {

    /**
     * 创建
     * @param captcha
     */
    void create(Captcha captcha);

    /**
     * 更新
     * @param captcha
     */
    void update(Captcha captcha);

    /**
     * 根据手机号码查询
     * @param cellphone
     * @return
     */
    Captcha queryByCellphone(String cellphone);

    /**
     * 单个查询
     * @param qo
     * @return
     */
    Captcha queryOne(CaptchaQO qo);

    /**
     * 集合查询
     * @param qo
     * @return
     */
    Captcha queryList(CaptchaQO qo);

}
