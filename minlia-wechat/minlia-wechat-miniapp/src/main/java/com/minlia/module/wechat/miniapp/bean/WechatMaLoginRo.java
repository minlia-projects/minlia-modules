package com.minlia.module.wechat.miniapp.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 小程序登陆 请求体
 *
 * @author garen
 */
@Data
@ApiModel
public class WechatMaLoginRo {

    @ApiModelProperty(value = "appid")
    @NotBlank
    @Size(max = 30)
    private String appid;

    @ApiModelProperty(value = "code")
    @NotBlank
    @Size(max = 32)
    private String code;

    @ApiModelProperty(value = "user data")
    @NotBlank
    private String userEncryptedData;

    @ApiModelProperty(value = "user iv")
    @NotBlank
    @Size(max = 24)
    private String userIv;

    @ApiModelProperty(value = "phone data")
    @NotBlank
    private String phoneEncryptedData;

    @ApiModelProperty(value = "phone iv")
    @NotBlank
    @Size(max = 24)
    private String phoneIv;

}