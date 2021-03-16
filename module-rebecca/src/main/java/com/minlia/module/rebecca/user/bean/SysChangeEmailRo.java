package com.minlia.module.rebecca.user.bean;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author garen
 * @date 2018/10/24
 */
@Data
public class SysChangeEmailRo {

    /**
     * 手机号码
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 验证码
     */
    @NotBlank
    private String vcode;

}