package com.minlia.module.pooul.bean.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.pooul.enumeration.PayStatusEnum;
import lombok.Data;

/**
 * 支付订单
 * Created by garen on 2018/8/25.
 */
@Data
public class PooulOrderDO extends AbstractEntity {

    /**
     * 与发起支付商户主体一致的小程序APPID
     * 必填
     */
//    @JsonProperty(value = "sub_appid")
    private String subAppid;

    /**
     * 用户在商户appid下的唯一标识
     * 必填
     */
    private String subOpenid;

    /**
     * 商户订单号，在同一个merchant_id 下每次请求必须为唯一，如：alextest.scan.113
     * 必填
     */
    private String mchTradeId;

    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;

    /**
     * 支付类型，不同的支付类型，pay_type值不一样。wechat.jsminipg：微信小程序支付
     * 必填
     */
    private String payType;

    /**
     * 随机字符串，在同一个merchant_id 下每次请求必须为唯一
     * 必填
     */
    private String nonceStr;

    /**
     * 支付总金额，单位为分，只能为整数，如：888 代表8.88元
     * 必填
     */
    private Integer totalFee;

    private String body;

    /**
     * 发起支付的终端IP，APP、jsapi、jsminipg、wap支付提交用户端ip，scan、micro支付填调用支付API的服务端IP。
     * 微信支付必填、支付宝选填
     */
    private String spbillCreateIp;

    /**
     * 支付结果通知地址，接收支付结果异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。如：http://pay.pooul.com/notify
     * 选填
     */
    private String notifyUrl;

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
