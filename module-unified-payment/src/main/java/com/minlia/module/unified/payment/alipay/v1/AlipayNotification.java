package com.minlia.module.unified.payment.alipay.v1;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AlipayNotification implements Serializable{
	private static final long serialVersionUID = -8638199167144867399L;

    private Integer alipayNoticeId;

	private String notifyId;

    private String notifyType;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date notifyTime;

    private String signType;

    private String sign;

    private String outTradeNo;

    private String subject;

    private String paymentType;

    private String tradeNo;

    private String tradeStatus;

    private String sellerId;

    private String sellerEmail;

    private String buyerId;

    private String buyerEmail;

    private Double totalFee;

    private Integer quantity;

    private Double price;

    private String body;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date gmtPayment;

    private String isTotalFeeAdjust;

    private String userCoupon;

    private String discount;

    private String refundStatus;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date gmtRefund;

    private Boolean verifyResult;

}