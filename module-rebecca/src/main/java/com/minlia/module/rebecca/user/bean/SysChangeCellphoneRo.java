package com.minlia.module.rebecca.user.bean;

import com.minlia.module.common.validation.Cellphone;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author garen
 * @date 2018/10/24
 */
@Data
public class SysChangeCellphoneRo {

    /**
     * 区域代码
     */
    @NotBlank
    @Size(max = 10)
    private String areaCode;

    /**
     * 手机号码
     */
    @NotBlank
    @Cellphone
    private String cellphone;

    /**
     * 验证码
     */
    @NotBlank
    private String vcode;

}