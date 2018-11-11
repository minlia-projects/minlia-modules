package com.minlia.module.wechat.ma.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by will on 6/24/17.
 */

@Data
@ApiModel(value = "小程序获取用户详情请求体")
public class MiniappUserDetailRequestBody {

    @ApiModelProperty(value = "用户CODE")
    @NotBlank
    private String code;

    @ApiModelProperty(value = "解密一次性向量, 每次需要请求")
    private String iv;

    @ApiModelProperty(value = "微信返回的加密码后的数据")
    private String encryptedData;

    @ApiModelProperty(value = "bible 小程序类型")
    @NotNull
    private String type;

}
