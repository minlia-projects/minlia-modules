package com.minlia.module.pooul.body.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 支付参数返回体
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulPayInfo {

//    {
//        "appId":"wx469ffdb81de47e4d",
//            "timeStamp":1532415674,
//            "nonceStr":"5b56ceba01c91130571ff1bf",
//            "package":"prepay_id=wx24150114085485cace47a4222474206550",
//            "signType":"MD5",
//            "paySign":"fb811e215f64f23ad2759195cb7406db"
//    }

    private String appId;

    private String timeStamp;

    private String nonceStr;

    @JsonProperty(value = "package")
    private String pkg;

    private String signType;

    private String paySign;

}

