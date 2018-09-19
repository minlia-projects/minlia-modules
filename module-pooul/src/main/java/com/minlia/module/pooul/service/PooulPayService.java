package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;

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
    Response wechatJsminipg(PooulWechatJsminipgTO requestBody);

    /**
     * 查询订单
     * @param mchTradeId
     */
    Response query(String mchTradeId);

    /**
     * 关闭订单
     * @param mchTradeId
     */
    Response close(String mchTradeId);

    /**
     * 撤销订单
     * @param mchTradeId
     */
    Response reverse(String mchTradeId);

}
