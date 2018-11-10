package com.minlia.module.unified.payment.body;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 498391498 on 2017/11/21.
 */
public enum PayType {

    @ApiModelProperty(name = "支付宝")
    ALIPAY,

    @ApiModelProperty(name = "微信")
    WECHAT

}
