package com.minlia.module.wechat.login.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Created by will on 6/24/17.
 */
@Data
public class WechatLoginRO {

    @ApiModelProperty(value = "微信CODE")
    @NotBlank
    private String code;

    @ApiModelProperty(value = "解密一次性向量, 每次需要请求")
    private String iv;

    @ApiModelProperty(value = "微信返回的加密码后的数据")
    private String encryptedData;

    @ApiModelProperty(value = "微信账号类型，区分多个小程序")
    private String type;

}
