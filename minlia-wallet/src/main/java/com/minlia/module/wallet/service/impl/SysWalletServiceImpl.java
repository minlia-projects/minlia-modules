package com.minlia.module.wallet.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.entity.SysWalletEntity;
import com.minlia.module.wallet.mapper.SysWalletMapper;
import com.minlia.module.wallet.service.SysWalletRecordService;
import com.minlia.module.wallet.service.SysWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 钱包 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Service
@RequiredArgsConstructor
public class SysWalletServiceImpl extends ServiceImpl<SysWalletMapper, SysWalletEntity> implements SysWalletService {

    private final SysWalletRecordService sysWalletRecordService;

    @Override
    public SysWalletEntity init(Long uid) {
        SysWalletEntity walletEntity = this.getByUid(uid);
        if (Objects.isNull(walletEntity)) {
            walletEntity = SysWalletEntity.builder().uid(uid).build();
            this.save(walletEntity);
        }
        return walletEntity;
    }

    @Override
    public SysWalletEntity update(WalletUro uro) {
        SysWalletEntity walletEntity = this.getByUid(uro.getUid());
        switch (uro.getType()) {
            case RECEIVABLES:
                this.receipts(uro, walletEntity);
                break;
            case PAY:
                this.pay(uro, walletEntity);
                break;
            case CANCEL_PAY:
                this.cancelPay(uro, walletEntity);
                break;
            case RECHARGE:
                this.recharge(uro, walletEntity);
                break;
            case REFUND:
                this.refund(uro, walletEntity);
                break;
            case WITHDRAW:
                this.withdraw(uro, walletEntity);
                break;
            case WITHDRAW_APPLY:
                this.withdrawApply(uro, walletEntity);
                break;
            case WITHDRAW_SETTLED:
                this.withdrawCompleted(uro, walletEntity);
        }
        this.sysWalletRecordService.create(walletEntity.getId(), uro);
        return walletEntity;
    }

    /**
     * 收款
     *
     * @param uro
     * @param walletEntity
     */
    private void receipts(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setFlow(walletEntity.getFlow().add(uro.getAmount()));
        walletEntity.setTotal(walletEntity.getTotal().add(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().add(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 付款
     *
     * @param uro
     * @param walletEntity
     */
    private void pay(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setTotal(walletEntity.getTotal().subtract(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().subtract(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 撤销付款，退还到余额
     *
     * @param uro
     * @param walletEntity
     */
    private void cancelPay(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setTotal(walletEntity.getTotal().add(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().add(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 充值
     *
     * @param uro
     * @param walletEntity
     */
    private void recharge(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setFlow(walletEntity.getFlow().add(uro.getAmount()));
        walletEntity.setTotal(walletEntity.getTotal().add(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().add(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 退款
     *
     * @param uro
     * @param walletEntity
     */
    public void refund(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setTotal(walletEntity.getTotal().subtract(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().subtract(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 直接提现
     *
     * @param uro
     * @param walletEntity
     */
    private void withdraw(WalletUro uro, SysWalletEntity walletEntity) {
        walletEntity.setTotal(walletEntity.getTotal().subtract(uro.getAmount()));
        walletEntity.setBalance(walletEntity.getBalance().subtract(uro.getAmount()));
        walletEntity.setWithdraw(walletEntity.getWithdraw().add(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 提现申请
     *
     * @param uro
     * @param walletEntity
     */
    private void withdrawApply(WalletUro uro, SysWalletEntity walletEntity) {
        //重置冻结金额
        walletEntity.setFrozen(walletEntity.getFrozen().add(uro.getAmount()));
        //重置余额
        walletEntity.setBalance(walletEntity.getBalance().subtract(uro.getAmount()));
        this.updateById(walletEntity);
    }

    /**
     * 提现完成
     *
     * @param uro
     * @param walletEntity
     */
    private void withdrawCompleted(WalletUro uro, SysWalletEntity walletEntity) {
        //重置总额
        walletEntity.setTotal(walletEntity.getTotal().subtract(uro.getAmount()));
        //重置冻结金额
        walletEntity.setFrozen(walletEntity.getFrozen().subtract(uro.getAmount()));
        //重置提现总额
        walletEntity.setWithdraw(walletEntity.getWithdraw().add(uro.getAmount()));
        this.updateById(walletEntity);
    }

//    @Override
//    public SysWalletEntity recharge(WalletRechargeRo ro) {
//        String outTradeNo = NumberGenerator.generatorByYMDHMSS("RO", 1);
//
//        UnifiedPayClient.create(ro.getChannel(), )
//
//        switch (ro.getChannel()) {
//            case wechat:
//                WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
//                        .outTradeNo(outTradeNo)
//                        .tradeType(ro.getTradeType())
//                        .totalFee(ro.getTotalFee().multiply(BigDecimal.valueOf(100)).intValue())
//                        .body("充值")
//                        .build();
//                if (ro.getTradeType().endsWith(WxPayConstants.TradeType.JSAPI)) {
//                    request.setOpenid("061myFDn1aAsYq0ZI3En1Z7QDn1myFD5");
//                }
//                WxPayMpOrderResult result = wxPayService.createOrder(request);
//                break;
//        }
//        return null;
//    }

    @Override
    public SysWalletEntity getByUid(Long uid) {
        return this.getOne(Wrappers.<SysWalletEntity>lambdaQuery().eq(SysWalletEntity::getUid, uid));
    }

}