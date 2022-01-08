package com.minlia.module.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egzosn.pay.common.bean.RefundResult;
import com.minlia.module.pay.bean.SysPayOrderCro;
import com.minlia.module.pay.bean.SysPayOrderDto;
import com.minlia.module.pay.entity.SysPayOrderEntity;


/**
 * <p>
 * 支付-订单 服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
public interface SysPayOrderService extends IService<SysPayOrderEntity> {

    /**
     * 创建
     * @param cro
     * @return
     */
    SysPayOrderDto create(SysPayOrderCro cro);

    Object getPayInfo(SysPayOrderCro cro);

    /**
     * 退款
     * @param orderNo
     * @return
     */
    RefundResult refund(String orderNo);

    void callback(String orderNo, String tradeNo);

    SysPayOrderEntity getByOrderNo(String orderNo);

    SysPayOrderEntity getByTradeNo(String tradeNo);

}