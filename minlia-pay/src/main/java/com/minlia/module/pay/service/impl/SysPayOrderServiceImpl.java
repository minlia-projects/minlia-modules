package com.minlia.module.pay.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.ali.bean.AliTransferType;
import com.egzosn.pay.common.bean.DefaultCurType;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.common.bean.TransferOrder;
import com.egzosn.pay.paypal.bean.PayPalTransactionType;
import com.egzosn.pay.spring.boot.core.PayServiceManager;
import com.egzosn.pay.spring.boot.core.bean.MerchantPayOrder;
import com.egzosn.pay.spring.boot.core.bean.MerchantQueryOrder;
import com.egzosn.pay.wx.v3.bean.WxTransactionType;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.module.currency.service.SysCurrencyRateService;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.pay.bean.*;
import com.minlia.module.pay.constant.SysPayCode;
import com.minlia.module.pay.entity.MerchantDetailsEntity;
import com.minlia.module.pay.entity.SysPayOrderEntity;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.enums.SysPayMethodEnum;
import com.minlia.module.pay.enums.SysPayStatusEnum;
import com.minlia.module.pay.event.SysPaidEvent;
import com.minlia.module.pay.mapper.SysPayOrderMapper;
import com.minlia.module.pay.service.MerchantDetailsService;
import com.minlia.module.pay.service.SysPayOrderService;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import com.minlia.module.wallet.service.SysWalletService;
import com.minlia.module.wallet.service.SysWalletWithdrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


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

    private final SysWalletService sysWalletService;
    private final PayServiceManager payServiceManager;
    private final MerchantDetailsService merchantDetailsService;
    private final SysCurrencyRateService sysCurrencyRateService;
    private final SysWalletWithdrawService sysWalletWithdrawService;

    @Override
    public Object getPayInfo(SysPayOrderCro cro) {
        MerchantDetailsEntity merchantDetailsEntity = merchantDetailsService.getByTypeAndMethod(cro.getChannel(), cro.getMethod());
        ApiAssert.notNull(merchantDetailsEntity, SysPayCode.Message.MERCHANT_NOT_EXISTS);

        BigDecimal actualAmount = cro.getAmount();
        String method = getMethod(cro.getChannel(), cro.getMethod());
        MerchantPayOrder payOrder = new MerchantPayOrder(merchantDetailsEntity.getDetailsId(), method, cro.getSubject(), cro.getBody(), actualAmount, cro.getOrderNo());
        payOrder.setCurType(DefaultCurType.CNY);
        if (Objects.nonNull(cro.getExpireTime())) {
            payOrder.setExpirationTime(LocalDateUtils.localDateTimeToDate(cro.getExpireTime()));
        }
        Object result = payServiceManager.toPay(payOrder);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPayOrderDto create(SysPayOrderCro cro) {
        SysPayOrderEntity entity = this.getByOrderNo(cro.getOrderNo());
        Object result;
        if (Objects.isNull(entity)) {
            entity = DozerUtils.map(cro, SysPayOrderEntity.class);
            if (SysPayChannelEnum.BALANCE == cro.getChannel()) {
                result = sysWalletService.update(WalletUro.builder()
                        .uid(cro.getUid())
                        .type(WalletOperationTypeEnum.OUT)
                        .amount(cro.getAmount())
                        .businessType(cro.getSubject())
                        .businessId(cro.getOrderNo())
                        .remark(cro.getBody())
                        .build());
                entity.setStatus(SysPayStatusEnum.PAID);
            } else {
                result = getPayInfo(cro);
                entity.setStatus(SysPayStatusEnum.UNPAID);
            }
            this.save(entity);
        } else {
            ApiAssert.state(SysPayStatusEnum.UNPAID.equals(entity.getStatus()), SysPayCode.Message.ORDER_ALREADY_FINISHED);
            if (SysPayChannelEnum.BALANCE == cro.getChannel()) {
                result = sysWalletService.update(WalletUro.builder()
                        .uid(cro.getUid())
                        .type(WalletOperationTypeEnum.OUT)
                        .amount(cro.getAmount())
                        .businessType(cro.getSubject())
                        .businessId(cro.getOrderNo())
                        .remark(cro.getBody())
                        .build());
                entity.setChannel(SysPayChannelEnum.BALANCE);
                entity.setStatus(SysPayStatusEnum.PAID);
            } else {
                result = getPayInfo(cro);
            }
            DozerUtils.map(cro, entity);
            this.updateById(entity);
        }
        //发布通知事件
        SysPaidEvent.onPaid(DozerUtils.map(entity, SysPaidResult.class));
        return SysPayOrderDto.builder().orderNo(cro.getOrderNo()).channel(cro.getChannel()).payload(result).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response close(String orderNo) {
        SysPayOrderEntity entity = this.getByOrderNo(orderNo);
        ApiAssert.notNull(entity, SysPayCode.Message.ORDER_NOT_EXISTS);
        ApiAssert.state(entity.getStatus().equals(SysPayStatusEnum.UNPAID), SysPayCode.Message.ORDER_ALREADY_FINISHED);

        MerchantDetailsEntity merchantDetailsEntity = merchantDetailsService.getByTypeAndMethod(entity.getChannel(), entity.getMethod());
        ApiAssert.notNull(merchantDetailsEntity, SysPayCode.Message.MERCHANT_NOT_EXISTS);
        MerchantQueryOrder queryOrder = new MerchantQueryOrder();
        queryOrder.setDetailsId(merchantDetailsEntity.getDetailsId());
        queryOrder.setTradeNo(entity.getTradeNo());
        queryOrder.setOutTradeNo(entity.getOrderNo());
        Map<String, Object> result = payServiceManager.close(queryOrder);
        return Response.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response refund(String orderNo) {
        SysPayOrderEntity entity = this.getByOrderNo(orderNo);
        ApiAssert.notNull(entity, SysPayCode.Message.ORDER_NOT_EXISTS);
        if (SysPayChannelEnum.BALANCE == entity.getChannel()) {
            boolean result = sysWalletService.update(WalletUro.builder()
                    .uid(entity.getUid())
                    .type(WalletOperationTypeEnum.IN)
                    .amount(entity.getAmount())
                    .businessType("REFUND")
                    .businessId(entity.getOrderNo())
                    .build());
            return Response.success(result);
        } else {
            MerchantDetailsEntity merchantDetailsEntity = merchantDetailsService.getByTypeAndMethod(entity.getChannel(), entity.getMethod());
            ApiAssert.notNull(merchantDetailsEntity, SysPayCode.Message.MERCHANT_NOT_EXISTS);
            RefundOrder refundOrder = new RefundOrder(IdWorker.getIdStr(), entity.getTradeNo(), entity.getAmount());
            return Response.success(payServiceManager.refund(merchantDetailsEntity.getDetailsId(), refundOrder));
        }
    }

    @Override
    public Response transfer(SysPayTransferOrder order) {
        TransferOrder transferOrder = new TransferOrder();
        if (order.getChannel() == SysPayChannelEnum.ALIPAY) {
            transferOrder.setTransferType(AliTransferType.TRANS_ACCOUNT_NO_PWD);
            transferOrder.setOutNo(IdWorker.getIdStr());
            transferOrder.setAmount(order.getAmount());
            transferOrder.addAttr("order_title", order.getSubject());
            transferOrder.setRemark(order.getRemark());
            Map<String, String> payeeInfo = Maps.newHashMap();
            payeeInfo.put("identity_type", "ALIPAY_LOGON_ID");
            payeeInfo.put("identity", order.getPayeeAccount());
            payeeInfo.put("name", order.getPayeeName());
            transferOrder.addAttr("payee_info", payeeInfo);
            Map<String, Object> result = payServiceManager.transfer("1", transferOrder);
            SysPayTransferResult transferResult = SysPayTransferResult.build(result);
            return Response.is(transferResult.isSuccess(), transferResult.getAlipayFundTransUniTransferResponse().getSubCode(), transferResult.getAlipayFundTransUniTransferResponse().getSubMsg());
        } else {
            return Response.failure(SysPayCode.Message.ONLY_SUPPORT_ALIPAY_TRANSFER);
        }
    }

    //{
    //    "alipay_fund_trans_uni_transfer_response": {
    //              "code": "10000",
    //            "msg": "Success",
    //            "out_biz_no": "201808080001",
    //            "order_id": "20190801110070000006380000250621",
    //            "pay_fund_order_id": "20190801110070001506380000251556",
    //            "status": "SUCCESS",
    //            "trans_date": "2019-08-21 00:00:00"
    //    },
    //    "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
    //}

    private String getMethod(SysPayChannelEnum channelEnum, SysPayMethodEnum methodEnum) {
        String method = null;
        if (channelEnum.equals(SysPayChannelEnum.ALIPAY)) {
            switch (methodEnum) {
                case PAGE:
                    method = AliTransactionType.PAGE.getType();
                    break;
                case QR:
                    method = AliTransactionType.BAR_CODE.getType();
                    break;
                case APP:
                    method = AliTransactionType.APP.getType();
                    break;
                case WAP:
                    method = AliTransactionType.WAP.getType();
                    break;
                case MINAPP:
                    method = AliTransactionType.MINAPP.getType();
                    break;
            }
        } else if (channelEnum.equals(SysPayChannelEnum.WECHAT)) {
            switch (methodEnum) {
                case PAGE:
                    method = WxTransactionType.JSAPI.getType();
                    break;
                case QR:
                    method = WxTransactionType.NATIVE.getType();
                    break;
                case APP:
                    method = WxTransactionType.APP.getType();
                    break;
                case WAP:
                    method = WxTransactionType.H5.getType();
                    break;
                case MINAPP:
                    method = WxTransactionType.JSAPI.getType();
                    break;
            }
        } else if (channelEnum.equals(SysPayChannelEnum.ALIPAY)) {
            method = PayPalTransactionType.sale.getType();
        }
        return method;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callback(String orderNo, String tradeNo) {
        log.info("支付回调更新状态开始===================={} {}", orderNo, tradeNo);
        SysPayOrderEntity orderEntity = this.getByOrderNo(orderNo);
        orderEntity.setTradeNo(tradeNo);
        orderEntity.setStatus(SysPayStatusEnum.PAID);
        this.updateById(orderEntity);
        log.info("支付回调更新状态完成====================");
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