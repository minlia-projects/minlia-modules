package com.minlia.module.wechat.miniapp.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by will on 6/24/17.
 */

@Data
@ApiModel(value = "绑定或注册请求体")
public class BindOrRegistrationRequestBody {

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "验证码")
    private String securityCode;

    @ApiModelProperty(value = "WeChat code")
    private String wxCode;

    //@ApiModelProperty(value = "微信返回来的openId")
    //private String openId;

}
