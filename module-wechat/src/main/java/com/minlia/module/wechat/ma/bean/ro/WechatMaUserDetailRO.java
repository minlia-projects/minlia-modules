package com.minlia.module.wechat.ma.bean.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by will on 6/24/17.
 */
@Data
@ApiModel(value = "小程序获取用户详情请求体")
public class WechatMaUserDetailRO extends WechatMaEncryptedDataRO {

    @ApiModelProperty(value = "bible 小程序类型")
    @NotNull
    private String type;

}