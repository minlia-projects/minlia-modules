package com.minlia.module.wechat.mp.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by will on 6/24/17.
 */
@Data
public class BindWxRequestBody {

    @ApiModelProperty(value = "登录名")
    @NotNull
    private String username;

    @ApiModelProperty(value = "验证码")
    @NotNull
    private String securityCode;

    @ApiModelProperty(value = "WeChat code")
    @NotNull
    private String wxCode;

}
