package com.minlia.module.rebecca.user.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author garen
 * @date 2018/10/24
 */
@Data
public class SysPasswordByCaptchaChangeTo extends SysPasswordChangeTo {

    /**
     * 验证码
     */
    @NotBlank
    private String vcode;

}
