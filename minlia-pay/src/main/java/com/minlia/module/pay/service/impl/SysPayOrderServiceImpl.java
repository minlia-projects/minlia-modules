package com.minlia.module.pay.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.DefaultCurType;
import com.egzosn.pay.paypal.bean.PayPalTransactionType;
import com.egzosn.pay.spring.boot.core.MerchantPayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.wx.bean.WxTransactionType;
import com.minlia.module.currency.service.SysCurrencyRateService;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.pay.bean.SysPaidResult;
import com.minlia.module.pay.bean.SysPayOrderCro;
import com.minlia.module.pay.bean.SysPayOrderDto;
import com.minlia.module.pay.entity.SysPayOrderEntity;
import com.minlia.module.pay.enums.SysPayStatusEnum;
import com.minlia.module.pay.event.SysPaidEvent;
import com.minlia.module.pay.mapper.SysPayOrderMapper;
import com.minlia.module.pay.service.SysPayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * <p>
 * 支付-订单 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPayOrderServiceImpl extends ServiceImpl<SysPayOrderMapper, SysPayOrderEntity> implements SysPayOrderService {

    private final SysCurrencyRateService sysCurrencyRateService;
    private final MerchantPayServiceManager merchantPayServiceManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPayOrderDto create(SysPayOrderCro cro) {
        SysPayOrderEntity orderEntity = DozerUtils.map(cro, SysPayOrderEntity.class);
        orderEntity.setStatus(SysPayStatusEnum.UNPAID);
        this.save(orderEntity);

        String result = "";
        BigDecimal actualAmount;
        switch (cro.getChannel()) {
            case ALIPAY:
                actualAmount = sysCurrencyRateService.convert(cro.getAmount(), cro.getCurrency(), DefaultCurType.CNY.name());
                MerchantPayOrder aliPayOrder = new MerchantPayOrder("1", AliTransactionType.PAGE.getType(), cro.getSubject(), cro.getBody(), actualAmount, cro.getOrderNo());
                aliPayOrder.setCurType(DefaultCurType.CNY);
                result = merchantPayServiceManager.toPay(aliPayOrder);
                break;
            case WECHAT:
                actualAmount = sysCurrencyRateService.convert(cro.getAmount(), cro.getCurrency(), DefaultCurType.CNY.name());
                MerchantPayOrder wxPayOrder = new MerchantPayOrder("2", WxTransactionType.NATIVE.getType(), cro.getSubject(), cro.getBody(), actualAmount, cro.getOrderNo());
                wxPayOrder.setCurType(DefaultCurType.CNY);
                result = merchantPayServiceManager.getQrPay(wxPayOrder);
                break;
            case PAYPAL:
                actualAmount = sysCurrencyRateService.convert(cro.getAmount(), cro.getCurrency(), DefaultCurType.USD.name());
                MerchantPayOrder paypalPayOrder = new MerchantPayOrder("3", PayPalTransactionType.sale.getType(), cro.getSubject(), cro.getBody(), actualAmount, cro.getOrderNo());
                paypalPayOrder.setCurType(DefaultCurType.USD);
                result = merchantPayServiceManager.toPay(paypalPayOrder);
                break;
            default:
        }
        return SysPayOrderDto.builder().orderNo(cro.getOrderNo()).channel(cro.getChannel()).payload(result).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callback(String orderNo, String tradeNo) {
        log.info("支付宝支付回调更新状态开始===================={} {}", orderNo, tradeNo);
        SysPayOrderEntity orderEntity = this.getByOrderNo(orderNo);
        orderEntity.setTradeNo(tradeNo);
        orderEntity.setStatus(SysPayStatusEnum.PAID);
        this.updateById(orderEntity);
        log.info("支付宝支付回调更新状态完成====================");
        //发布通知事件
        SysPaidEvent.onPaid(DozerUtils.map(orderEntity, SysPaidResult.class));
    }

    @Override
    public SysPayOrderEntity getByOrderNo(String orderNo) {
        return this.getOne(Wrappers.<SysPayOrderEntity>lambdaQuery().eq(SysPayOrderEntity::getOrderNo, orderNo));
    }

    @Override
    public SysPayOrderEntity getByTradeNo(String tradeNo) {
        return this.getOne(Wrappers.<SysPayOrderEntity>lambdaQuery().eq(SysPayOrderEntity::getTradeNo, tradeNo));
    }

}