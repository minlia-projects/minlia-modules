package com.minlia.module.pooul.bean.to;

import lombok.Data;

/**
 * 微信小程序支付请求体
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulWechatJsminipgTO extends PooulBaseOrderTO {

    /**
     * 用户在商户appid下的唯一标识
     * 必填
     */
    private String subOpenid;

    /**
     * 与发起支付商户主体一致的小程序APPID
     * 必填
     */
    private String subAppid;

}
