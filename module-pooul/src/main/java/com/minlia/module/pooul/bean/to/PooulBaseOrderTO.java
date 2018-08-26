package com.minlia.module.pooul.bean.to;

import lombok.Data;

/**
 * Pooul 统一支付 接口公共请求参数
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulBaseOrderTO {

//    /**
//     * 商户号  merchant_id	是	String(16)	发起支付的商户编号，16位数字，由普尔瀚达分配
//     * url?merchant_id=XXXX
//     */
//    @JsonIgnore
//    private String merchantId;

    /**
     * 支付类型，不同的支付类型，pay_type值不一样。wechat.jsminipg：微信小程序支付
     * 必填
     */
//    @SerializedName("pay_type")
//    @JsonProperty("pay_type")
    private String payType;

    /**
     * 随机字符串，在同一个merchant_id 下每次请求必须为唯一
     * 必填
     */
    private String nonceStr;

    /**
     * 商户订单号，在同一个merchant_id 下每次请求必须为唯一，如：alextest.scan.113
     * 必填
     */
    private String mchTradeId;

    /**
     * 支付总金额，单位为分，只能为整数，如：888 代表8.88元
     * 必填
     */
    private Integer totalFee;

    private String body;

    /**
     * 支付结果通知地址，接收支付结果异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。如：http://pay.pooul.com/notify
     * 选填
     */
    private String notifyUrl;

    /**
     * 发起支付的终端IP，APP、jsapi、jsminipg、wap支付提交用户端ip，scan、micro支付填调用支付API的服务端IP。
     * 微信支付必填、支付宝选填
     */
    private String spbillCreateIp;

    /**
     * 订单开始时间，为10位 UNIX 时间戳，如：1530759545
     * 选填
     */
    private Integer timeStart;

    /**
     * 订单失效时间，为10位 UNIX 时间戳，如：1530759574
     * 选填
     */
    private Integer timeExpire;

    /**
     * 终端设备号(门店号或收银设备ID)，注意：PC网页或APP支付请传"WEB"
     * 选填
     */
    private String deviceInfo;

    /**
     * 操作员或收银员编号
     * 选填
     */
    private String opUserId;

}
