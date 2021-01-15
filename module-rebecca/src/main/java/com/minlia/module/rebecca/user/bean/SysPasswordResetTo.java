package com.minlia.module.rebecca.user.bean;

import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import com.minlia.module.rebecca.user.enums.SysPasswordResetTypeEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author garen
 * @date 2018/10/24
 */
@Data
public class SysPasswordResetTo extends SysPasswordChangeTo {

    @NotNull
    private SysPasswordResetTypeEnum type;

    /**
     * 原密码
     */
    @Password
    private String rawPassword;

    private String areaCode;

    @Cellphone
    private String cellphone;

    @Email
    private String email;

    @NotBlank
    private String vcode;

}