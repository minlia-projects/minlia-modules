package com.minlia.module.pooul.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.pooul.body.pay.PooulWechatJsminipgRequestBody;

/**
 * Created by garen on 2018/7/18.
 */
public interface PooulPayService {

    /**
     * 微信小程序支付 wechat.jsminipg
     *
     * @param requestBody
     * @return
     */
    StatefulBody wechatJsminipg(PooulWechatJsminipgRequestBody requestBody);

    /**
     * 关闭订单
     * @param mchTradeId
     */
    void close(String mchTradeId);

    /**
     * 撤销订单
     * @param mchTradeId
     */
    void reverse(String mchTradeId);

}
