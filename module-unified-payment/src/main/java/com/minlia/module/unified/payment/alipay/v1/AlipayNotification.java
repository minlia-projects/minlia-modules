package com.minlia.module.unified.payment.alipay.v1;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AlipayNotification implements Serializable {
    private static final long serialVersionUID = -8638199167144867399L;

    private Integer alipayNoticeId;

    private String notifyId;

    private String notifyType;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime notifyTime;

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

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime gmtCreate;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime gmtPayment;

    private String isTotalFeeAdjust;

    private String userCoupon;

    private String discount;

    private String refundStatus;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime gmtRefund;

    private Boolean verifyResult;

}