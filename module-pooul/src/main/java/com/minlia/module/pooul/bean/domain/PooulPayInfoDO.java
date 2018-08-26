package com.minlia.module.pooul.bean.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 支付参数
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulPayInfoDO {

//    {
//        "appId":"wx469ffdb81de47e4d",
//            "timeStamp":1532415674,
//            "nonceStr":"5b56ceba01c91130571ff1bf",
//            "package":"prepay_id=wx24150114085485cace47a4222474206550",
//            "signType":"MD5",
//            "paySign":"fb811e215f64f23ad2759195cb7406db"
//    }

    /**
     * 商户订单号
     */
    private String mchTradeId;

    private String appId;

    @SerializedName(value = "timeStamp")
    @JsonProperty(value = "timeStamp")
    private String timestamp;

    private String nonceStr;

    @SerializedName(value = "package")
    @JsonProperty(value = "package")
    private String pkg;

    private String signType;

    private String paySign;

}

