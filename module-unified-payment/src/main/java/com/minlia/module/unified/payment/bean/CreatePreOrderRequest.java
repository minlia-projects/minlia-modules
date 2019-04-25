package com.minlia.module.unified.payment.bean;

import com.minlia.cloud.body.Body;
import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
import com.minlia.module.unified.payment.enumeration.PayOperationEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * APP支付, 扫码支付时使用
 */
@Data
@ApiModel("创建支付预订单请求体")
public class CreatePreOrderRequest implements Body {

    @ApiModelProperty(value = "订单号", example = "xxxxxxxx")
    @Size(max = 32)
    private String number;

    @ApiModelProperty(value = "交易通道", example = "wechat,alipay")
    @NotNull
    private PayChannelEnum channel;

    @ApiModelProperty(value = "订单金额(分)", example = "1")
    @NotNull
    private Integer amount;

    @ApiModelProperty(value = "操作", example = "RECHARGE")
    @NotNull
    private PayOperationEnum operation;

    @ApiModelProperty(value = "支付标题(产品标题)", example = "测试商口")
    private String subject;

    @ApiModelProperty(value = "支付的内容 描述性说明文字", example = "支付测试")
    private String body;

    private String attach;

    @ApiModelProperty(value = "微信交易类型", example = "APP,JSAPI")
    private String tradeType;
//    WxPayConstants.TradeType



    /**
     * 微信 商品ID trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    private String productId;

    /**
     * 微信 用户标识 trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
     */
    private String openid;

}

