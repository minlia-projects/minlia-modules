package com.minlia.module.pooul.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by garen on 2018/8/27.
 */
@Data
public class PooulOrderQueryDTO implements Serializable{

//    {"trade_id":"5b7d182d01c9117a6243b153","mch_trade_id":"O2018082215595946327","merchant_id":"2162288807443437","pay_type":"wechat.jsminipg","trade_state":3,"trade_info":"已关闭, 订单已关闭,SUCCESS,OK"}

    @SerializedName(value = "trade_id")
    @JsonProperty(value = "trade_id")
    private String trade_id;

    @SerializedName(value = "mch_trade_id")
    private String mch_trade_id;

    @SerializedName(value = "merchant_id")
    private String merchant_id;

    @SerializedName(value = "pay_type")
    private String pay_type;

    /*
     * 0：支付成功、2：未支付、3：已关闭
     */
    private Integer trade_state;

    @SerializedName(value = "trade_info")
    private String trade_info;

}
