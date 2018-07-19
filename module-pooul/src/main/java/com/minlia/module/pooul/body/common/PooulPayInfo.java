package com.minlia.module.pooul.body.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 支付参数返回体
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulPayInfo {
//  {"appId":"wx26a0ffd6bfa99df7",
// "timeStamp":"1526429363","nonce_str":"hXJ2e3dQZm8qwZlc","package":"prepay_id=wx1608092325202618ae9afa6d0781719733","signType":"MD5","paySign":"94095737486E1394D9534599B7A978D5"}

    private String appId;

    private String timeStamp;

    private String nonceStr;

    @JsonProperty(value = "package")
    private String pkg;

    private String signType;

    private String paySign;

}

