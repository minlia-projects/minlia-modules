package com.minlia.module.aliyun.market.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class BankCardVerifyDto {

//    {
//        "resp": {
//        "code": 0,
//                "desc": "OK"
//    },
//        "data": {
//        "bank_id": "03050001",
//                "card_name": "民生信用卡(银联卡)",
//                "card_type": "贷记卡",
//                "bank_phone": "95568",
//                "bank_name": "民生银行",
//                "bank_logo": "http://lundroid.com/banklogo/1667d2.png",
//                "bank_url": "http://www.cmbc.com.cn"
//    }
//    }

    private Resp resp;

    private Data data;

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @lombok.Data
    public class Resp {

        private Integer code;

        private String desc;

    }

    public Boolean isSuccess(){
        return resp.code.equals(0);
    }

    @lombok.Data
    public class Data {

        @SerializedName("bank_id")
        @JsonProperty("bank_id")
        private String bankId;

        @SerializedName("card_name")
        @JsonProperty("card_name")
        private String cardName;

        @SerializedName("card_type")
        @JsonProperty("card_type")
        private String cardType;

        @SerializedName("bank_phone")
        @JsonProperty("bank_phone")
        private String bankPhone;

        @SerializedName("bank_name")
        @JsonProperty("bank_name")
        private String bankName;

        @SerializedName("bank_logo")
        @JsonProperty("bank_logo")
        private String bankLogo;

        @SerializedName("bank_url")
        @JsonProperty("bank_url")
        private String bankUrl;

    }

}
