package com.minlia.module.wechat.login.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by will on 6/24/17.
 */
@Data
public class WechatBindRO {

    @ApiModelProperty(value = "登录名")
    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8,max = 16)
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank
    private String securityCode;

    @ApiModelProperty(value = "WeChat code")
    @NotBlank
    private String wxCode;

}