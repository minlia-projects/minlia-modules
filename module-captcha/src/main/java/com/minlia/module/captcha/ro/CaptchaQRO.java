package com.minlia.module.captcha.ro;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * Created by garen on 2018/1/23.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaQRO extends QueryRequest {

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
    private String code;

    /**
     * 是否已被使用
     */
    private Boolean used = false;

    /**
     * 是否锁定
     */
    private Boolean locked;

    private Boolean enabled;

    private String createBy;

    private String lastModifiedBy;

}
