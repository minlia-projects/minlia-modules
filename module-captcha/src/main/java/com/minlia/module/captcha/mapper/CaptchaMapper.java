package com.minlia.module.captcha.mapper;

import com.minlia.module.captcha.body.CaptchaQueryRequestBody;
import com.minlia.module.captcha.entity.Captcha;
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
     * @param body
     * @return
     */
    Captcha queryOne(CaptchaQueryRequestBody body);

    /**
     * 集合查询
     * @param body
     * @return
     */
    Captcha queryList(CaptchaQueryRequestBody body);

    /**
     * 分页查询
     * @param body
     * @return
     */
    Captcha queryPaginated(CaptchaQueryRequestBody body);

}
