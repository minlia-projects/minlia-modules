package com.minlia.module.captcha.bean;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.data.entity.BaseQueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * @author garen
 * @date 2018/1/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaQro extends BaseQueryEntity {

    /**
     * 手机号码
     */
    @Cellphone
    private String cellphone;

    /**
     * 邮箱地址
     */
    @Email
    private String email;

    /**
     * 验证码内容
     */
    private String vcode;

    /**
     * 是否已被使用
     */
    private Boolean useFlag;

    /**
     * 是否锁定
     */
    private Boolean lockFlag;

}