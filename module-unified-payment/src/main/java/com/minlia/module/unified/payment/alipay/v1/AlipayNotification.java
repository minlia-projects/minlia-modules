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

//    public String getNotifyId() {
//        return notifyId;
//    }
//
//    public void setNotifyId(String notifyId) {
//        this.notifyId = notifyId == null ? null : notifyId.trim();
//    }
//
//    public String getNotifyType() {
//        return notifyType;
//    }
//
//    public void setNotifyType(String notifyType) {
//        this.notifyType = notifyType == null ? null : notifyType.trim();
//    }
//
//    public Date getNotifyTime() {
//        return notifyTime;
//    }
//
//    public void setNotifyTime(Date notifyTime) {
//        this.notifyTime = notifyTime;
//    }
//
//    public String getSignType() {
//        return signType;
//    }
//
//    public void setSignType(String signType) {
//        this.signType = signType == null ? null : signType.trim();
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign == null ? null : sign.trim();
//    }
//
//    public String getOutTradeNo() {
//        return outTradeNo;
//    }
//
//    public void setOutTradeNo(String outTradeNo) {
//        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject == null ? null : subject.trim();
//    }
//
//    public String getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(String paymentType) {
//        this.paymentType = paymentType == null ? null : paymentType.trim();
//    }
//
//    public String getTradeNo() {
//        return tradeNo;
//    }
//
//    public void setTradeNo(String tradeNo) {
//        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
//    }
//
//    public String getTradeStatus() {
//        return tradeStatus;
//    }
//
//    public void setTradeStatus(String tradeStatus) {
//        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
//    }
//
//    public String getSellerId() {
//        return sellerId;
//    }
//
//    public void setSellerId(String sellerId) {
//        this.sellerId = sellerId == null ? null : sellerId.trim();
//    }
//
//    public String getSellerEmail() {
//        return sellerEmail;
//    }
//
//    public void setSellerEmail(String sellerEmail) {
//        this.sellerEmail = sellerEmail == null ? null : sellerEmail.trim();
//    }
//
//    public String getBuyerId() {
//        return buyerId;
//    }
//
//    public void setBuyerId(String buyerId) {
//        this.buyerId = buyerId == null ? null : buyerId.trim();
//    }
//
//    public String getBuyerEmail() {
//        return buyerEmail;
//    }
//
//    public void setBuyerEmail(String buyerEmail) {
//        this.buyerEmail = buyerEmail == null ? null : buyerEmail.trim();
//    }
//
//    public Double getTotalFee() {
//        return totalFee;
//    }
//
//    public void setTotalFee(Double totalFee) {
//        this.totalFee = totalFee;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body == null ? null : body.trim();
//    }
//
//    public Date getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public void setGmtCreate(Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Date getGmtPayment() {
//        return gmtPayment;
//    }
//
//    public void setGmtPayment(Date gmtPayment) {
//        this.gmtPayment = gmtPayment;
//    }
//
//    public String getIsTotalFeeAdjust() {
//        return isTotalFeeAdjust;
//    }
//
//    public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
//        this.isTotalFeeAdjust = isTotalFeeAdjust == null ? null : isTotalFeeAdjust.trim();
//    }
//
//    public String getUserCoupon() {
//        return userCoupon;
//    }
//
//    public void setUserCoupon(String userCoupon) {
//        this.userCoupon = userCoupon == null ? null : userCoupon.trim();
//    }
//
//    public String getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(String discount) {
//        this.discount = discount == null ? null : discount.trim();
//    }
//
//    public String getRefundStatus() {
//        return refundStatus;
//    }
//
//    public void setRefundStatus(String refundStatus) {
//        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
//    }
//
//    public Date getGmtRefund() {
//        return gmtRefund;
//    }
//
//    public void setGmtRefund(Date gmtRefund) {
//        this.gmtRefund = gmtRefund;
//    }
//
//	public Integer getAlipayNoticeId() {
//
//		return alipayNoticeId;
//	}
//
//	public void setAlipayNoticeId(Integer alipayNoticeId) {
//
//		this.alipayNoticeId = alipayNoticeId;
//	}
//
//	public Boolean getVerifyResult() {
//
//		return verifyResult;
//	}
//
//	public void setVerifyResult(Boolean verifyResult) {
//
//		this.verifyResult = verifyResult;
//	}


}