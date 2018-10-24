package com.minlia.module.captcha.mapper;

import com.minlia.module.captcha.bean.domain.Captcha;
import com.minlia.module.captcha.bean.qo.CaptchaQO;
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
     * 计数查询
     * @param cellphone
     * @return
     */
    long count(String cellphone);

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
