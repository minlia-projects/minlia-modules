package com.minlia.module.wechat.mp.body;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by will on 6/24/17.
 */
@Data
public class LoginWechatRequestBody {

    @ApiModelProperty(value = "WeChat code")
    @NotNull
    private String code;

    @ApiModelProperty(value = "类型，区分不用的小程序")
    private String type;

}
