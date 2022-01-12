package com.minlia.module.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.cloud.body.Response;
import com.minlia.module.pay.bean.SysPayOrderCro;
import com.minlia.module.pay.bean.SysPayOrderDto;
import com.minlia.module.pay.bean.SysPayTransferOrder;
import com.minlia.module.pay.entity.SysPayOrderEntity;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


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
     * 获取支付信息
     *
     * @param cro 支付参数
     * @return 支付信息
     */
    Object getPayInfo(SysPayOrderCro cro);

    /**
     * 创建
     *
     * @param cro 支付参数
     * @return 支付信息
     */
    SysPayOrderDto create(SysPayOrderCro cro);

    /**
     * 关闭
     *
     * @param orderNo
     * @return
     */
    Response close(String orderNo);

    /**
     * 退款
     *
     * @param orderNo 订单号
     * @return 退款结果
     */
    Response refund(String orderNo);

    /**
     * 转账
     *
     * @param order
     * @return
     */
    Response transfer(SysPayTransferOrder order);

    /**
     * 回调
     *
     * @param orderNo 订单号
     * @param tradeNo 交易号
     */
    void callback(String orderNo, String tradeNo);

    /**
     * 根据订单号查询
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    SysPayOrderEntity getByOrderNo(String orderNo);

    /**
     * 根据交易号查询
     *
     * @param tradeNo 交易号
     * @return 订单信息
     */
    SysPayOrderEntity getByTradeNo(String tradeNo);

}