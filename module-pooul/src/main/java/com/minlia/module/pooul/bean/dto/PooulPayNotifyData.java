package com.minlia.module.pooul.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by garen on 2018/07/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PooulPayNotifyData implements PooulApiHttpDTO {

//    {
//        "bank_type":"CFT",
//        "cash_fee":"1",
//        "fee_type":"CNY",
//        "out_trade_id":"4200000109201807243048279688",
//        "sub_is_subscribe":"N",
//        "sub_openid":"oerQA5Q5clTAK8eA3tGNOAiz7s4o",
//        "total_fee":"1",
//        "trade_id":"5b56cd9b01c911305d1ff1ca",
//        "mch_trade_id":"n4GBxRbzXM",
//        "merchant_id":"2162288807443437",
//        "pay_type":"wechat.jsminipg",
//        "trade_state":"0",
//        "trade_info":"交易成功"
//    }

    @JsonProperty("bank_type")
    private String bankType;

    @JsonProperty("cash_fee")
    private Integer cashFee;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("out_trade_id")
    private String outTradeId;

    @JsonProperty("sub_is_subscribe")
    private String subIsSubscribe;

    @JsonProperty("sub_openid")
    private String subOpenid;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("trade_id")
    private String tradeId;

    @JsonProperty("mch_trade_id")
    private String mchTradeId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("pay_type")
    private String payType;

    @JsonProperty("trade_state")
    private Integer tradeState;

    @JsonProperty("trade_info")
    private String tradeInfo;

    @Override
    public Boolean isSuccess() {
        return null != tradeState && tradeState.equals(NumberUtils.INTEGER_ZERO);
    }

//     "actual_fee"

}
