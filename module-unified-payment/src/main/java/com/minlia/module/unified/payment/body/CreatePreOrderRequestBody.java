package com.minlia.module.unified.payment.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * APP支付, 扫码支付时使用
 */
@Data
@ApiModel("创建支付预订单请求体")
public class CreatePreOrderRequestBody implements Body {

    @ApiModelProperty(value = "交易通道", example = "wechat,alipay")
    @NotNull
    @JsonProperty
    private String gateway;

    @ApiModelProperty(value = "订单金额", example = "1")
    @NotNull
    @JsonProperty
    private Integer amount;

    @ApiModelProperty(value = "支付标题(产品标题)", example = "测试商口")
    @JsonProperty
    private String subject;

    @ApiModelProperty(value = "支付的内容 描述性说明文字", example = "支付测试")
    @JsonProperty
    private String body;

    /**
     * String number = OrderNumberUtil.generateOrderNumberTimestamp("A");
     */
    @ApiModelProperty(value = "支付的内容 描述性说明文字", example = "订单号")
    @JsonProperty
    private String number;

    /**
     * 为了适应小程序和APP都可以使用, 添加了此参数, 需要在调用的时候指定, 如未指定则使用app支付 方式
     */
    @JsonProperty
    @ApiModelProperty(value = "为了适应小程序和APP都可以使用, 添加了此参数, 需要在调用的时候指定, 如未指定则使用app支付方式", example = "APP,JSAPI")
    private String tradeType;

    /**
     * 商品ID 微信页面支付需要传商品ID
     */
    @JsonIgnore
    private String productId;

}

