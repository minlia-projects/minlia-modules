package com.minlia.module.pay.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.minlia.module.pay.enums.SysPayStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author garen
 */
@NoArgsConstructor
@Data
public class SysAlipayOrderDto {

    //{
    //    "alipay_trade_query_response": {
    //            "msg": "Success",
    //            "code": "10000",
    //            "buyer_user_id": "2088632338986864",
    //            "send_pay_date": "2022-04-25 15:59:09",
    //            "invoice_amount": "0.00",
    //            "out_trade_no": "1518499351849504770",
    //            "total_amount": "59.00",
    //            "trade_status": "TRADE_SUCCESS",
    //            "trade_no": "2022042522001486861441036521",
    //            "buyer_logon_id": "132******39",
    //            "receipt_amount": "0.00",
    //            "point_amount": "0.00",
    //            "buyer_pay_amount": "0.00"
    //    },
    //    "alipay_cert_sn": "f8c7c9179064683fdbf9b068acbd6241",
    //    "sign": "OyGkZA8S4Pu50AeLxVR5g4ZKSwRo1j/uNYjicp4Zk3Xr8qjFTdRzT8VvcRVKZGkPTwOTi3Eaxiq5ES6s+2+WT9osLB6a+5yEGSTOOtoNcf2ixRiltCsQXr/yghH6ko3Isbrs0WvOxyi7R3rYcW4vjzAzB2llyP8TQn+1RIVfopwg2t1fW0kBkHCzDsxk2I87BnBVDNY0SBeT7ayzCccYvTghqBu8j4Xna51sZyWDTFUb4i6vMcZhs1vlEMd/CNWKwXRlB7UQbZhyEtqFWTl+gt5FFHtAshxMjTXtWTRxnsCPuq9W6uiG0ID2YPBEW3EJAsTx86xx6Gn2q3xQJt6TFw=="
    //}

    //{"alipay_trade_query_response":{"msg":"Business Failed","code":"40004","out_trade_no":"1518484983355183105","sub_msg":"交易不存在","sub_code":"ACQ.TRADE_NOT_EXIST","receipt_amount":"0.00","point_amount":"0.00","buyer_pay_amount":"0.00","invoice_amount":"0.00"},"alipay_cert_sn":"f8c7c9179064683fdbf9b068acbd6241","sign":"HKdDsIew0jfep/dlWdOxAGiO2YJhZXAlLkdPC3hJN851MmfU6Hmabgby6t0m+glz0vtGQjCJn0vBQ/Gf0B5dpvlZJrs3XlMIr+pPU5L1cYfVdP1PabEKhUwBOIIBo8fWNYWvibQ1YAgn9KEeC2R4L+ddgIrhklSIu4TvNuVNi2JZQj3SOEltgMrAJWCEp7uHUlJE8iW1VoUjxK1yUW7TVnd9HVg9U8I/Lv2ECR16iNHD4pgABS11MFXVKtbqAVxFGcvVwZKbJ5qh9Ot4bYmnTjvuvEOcWj+q+vvyEoQjSOsn/h4EDI56PdHX0QX0vlcUkfp2cYF7lk/dZc7IoaWeCA\u003d\u003d"}

    private final static String SUCCESS_CODE = "10000";
    private final static String UNPAID_CODE = "40004";

    @JsonProperty("alipay_trade_query_response")
    private AlipayTradeQueryResponseDTO alipayTradeQueryResponse;
    @JsonProperty("alipay_cert_sn")
    private String alipayCertSn;
    @JsonProperty("sign")
    private String sign;

    @NoArgsConstructor
    @Data
    public static class AlipayTradeQueryResponseDTO {
        @JsonProperty("msg")
        private String msg;
        @JsonProperty("code")
        private String code;
        @JsonProperty("buyer_user_id")
        private String buyerUserId;
        @JsonProperty("send_pay_date")
        private LocalDateTime sendPayDate;
        @JsonProperty("invoice_amount")
        private BigDecimal invoiceAmount;
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        @JsonProperty("total_amount")
        private BigDecimal totalAmount;
        @JsonProperty("trade_status")
        private String tradeStatus;
        @JsonProperty("trade_no")
        private String tradeNo;
        @JsonProperty("buyer_logon_id")
        private String buyerLogonId;
        @JsonProperty("receipt_amount")
        private BigDecimal receiptAmount;
        @JsonProperty("point_amount")
        private BigDecimal pointAmount;
        @JsonProperty("buyer_pay_amount")
        private BigDecimal buyerPayAmount;
    }

    public boolean isSuccess() {
        return alipayTradeQueryResponse.getCode().equals(SUCCESS_CODE);
    }

    public boolean isUnpaid() {
        return alipayTradeQueryResponse.getCode().equals(UNPAID_CODE);
    }

    public SysPayStatusEnum getStatus() {
        SysPayStatusEnum status;
        switch (alipayTradeQueryResponse.getTradeStatus()) {
            case "TRADE_CLOSED":
                status = Objects.isNull( alipayTradeQueryResponse.getSendPayDate()) ? SysPayStatusEnum.CANCELED : SysPayStatusEnum.REFUND;
                break;
            case "TRADE_SUCCESS":
            case "TRADE_FINISHED":
                status = SysPayStatusEnum.PAID;
                break;
            default:
                status = SysPayStatusEnum.UNPAID;
        }
        return status;
    }

    //交易状态：
    //WAIT_BUYER_PAY（交易创建，等待买家付款）、
    //TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
    //TRADE_SUCCESS（交易支付成功）、
    //TRADE_FINISHED（交易结束，不可退款）

}