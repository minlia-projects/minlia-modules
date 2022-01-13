package com.minlia.module.member.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SysMemberPasswordSro {

    @ApiModelProperty(value = "二级密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 8)
    private String password;

    @ApiModelProperty(value = "验证码", example = "8888")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 8)
    private String captcha;

}