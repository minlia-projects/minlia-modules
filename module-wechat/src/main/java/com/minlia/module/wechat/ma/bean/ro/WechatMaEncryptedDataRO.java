package com.minlia.module.wechat.ma.bean.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by will on 9/12/17.
 */
@Data
@ApiModel(value = "获取用户加密信息请求体")
public class WechatMaEncryptedDataRO {

    /**
     * 用户CODE
     */
    @ApiModelProperty(value = "用户CODE")
    private String code;

    /**
     * 解密一次性向量, 每次需要请求
     */
    @ApiModelProperty(value = "解密一次性向量, 每次需要请求")
    private String iv;

    /**
     * 微信返回的加密码后的数据
     */
    @ApiModelProperty(value = "微信返回的加密码后的数据")
    private String encryptedData;

}
