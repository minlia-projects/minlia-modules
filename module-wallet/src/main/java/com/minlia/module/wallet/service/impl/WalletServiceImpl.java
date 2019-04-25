package com.minlia.module.wallet.service.impl;

import com.minlia.module.wallet.bean.entity.Wallet;
import com.minlia.module.wallet.bean.ro.WalletRechargeRO;
import com.minlia.module.wallet.bean.ro.WalletURO;
import com.minlia.module.wallet.mapper.WalletMapper;
import com.minlia.module.wallet.service.WalletHistoryService;
import com.minlia.module.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by user on 3/13/17.
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private WalletHistoryService walletHistoryService;

//    @Autowired
//    private WxPayService wxPayService;

    @Override
    public Wallet init(String guid) {
        Wallet wallet = walletMapper.queryByGuid(guid);
        if (null == wallet) {
            wallet = Wallet.builder()
                    .guid(guid)
                    .totalAmount(BigDecimal.ZERO)
                    .flowAmount(BigDecimal.ZERO)
                    .balanceAmount(BigDecimal.ZERO)
                    .frozenAmount(BigDecimal.ZERO)
                    .withdrawAmount(BigDecimal.ZERO)
                    .build();
            walletMapper.create(wallet);
        }
        return wallet;
    }

    public Wallet update(WalletURO uro) {
        Wallet wallet = walletMapper.queryByGuid(uro.getGuid());
        switch (uro.getOperationType()) {
            case RECEIVABLES:
                this.receipts(uro,wallet);
                break;
            case PAY:
                this.pay(uro,wallet);
                break;
            case CANCEL_PAY:
                this.cancelPay(uro,wallet);
                break;
            case RECHARGE:
                this.recharge(uro,wallet);
                break;
            case REFUND:
                this.refund(uro,wallet);
                break;
            case WITHDRAW:
                this.withdraw(uro,wallet);
                break;
            case WITHDRAW_APPLY:
                this.withdrawApply(uro,wallet);
                break;
            case WITHDRAW_SETTLED:
                this.withdrawCompleted(uro,wallet);
        }
        this.walletHistoryService.create(uro,wallet.getId());
        return wallet;
    }

    /**
     * 收款
     *
     * @param uro
     * @param wallet
     */
    private void receipts(WalletURO uro, Wallet wallet) {
        wallet.setFlowAmount(wallet.getFlowAmount().add(uro.getAmount()));
        wallet.setTotalAmount(wallet.getTotalAmount().add(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().add(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 付款
     *
     * @param uro
     * @param wallet
     */
    private void pay(WalletURO uro, Wallet wallet) {
        wallet.setTotalAmount(wallet.getTotalAmount().subtract(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 撤销付款，退还到余额
     *
     * @param uro
     * @param wallet
     */
    private void cancelPay(WalletURO uro, Wallet wallet) {
        wallet.setTotalAmount(wallet.getTotalAmount().add(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().add(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 充值
     *
     * @param uro
     * @param wallet
     */
    private void recharge(WalletURO uro, Wallet wallet) {
        wallet.setFlowAmount(wallet.getFlowAmount().add(uro.getAmount()));
        wallet.setTotalAmount(wallet.getTotalAmount().add(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().add(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 退款
     *
     * @param uro
     * @param wallet
     */
    public void refund(WalletURO uro, Wallet wallet) {
        wallet.setTotalAmount(wallet.getTotalAmount().subtract(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 直接提现
     *
     * @param uro
     * @param wallet
     */
    private void withdraw(WalletURO uro, Wallet wallet) {
        wallet.setTotalAmount(wallet.getTotalAmount().subtract(uro.getAmount()));
        wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(uro.getAmount()));
        wallet.setWithdrawAmount(wallet.getWithdrawAmount().add(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 提现申请
     *
     * @param uro
     * @param wallet
     */
    private void withdrawApply(WalletURO uro, Wallet wallet) {
        //重置冻结金额
        wallet.setFrozenAmount(wallet.getFrozenAmount().add(uro.getAmount()));
        //重置余额
        wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(uro.getAmount()));
        walletMapper.update(wallet);
    }

    /**
     * 提现完成
     *
     * @param uro
     * @param wallet
     */
    private void withdrawCompleted(WalletURO uro, Wallet wallet) {
        //重置总额
        wallet.setTotalAmount(wallet.getTotalAmount().subtract(uro.getAmount()));
        //重置冻结金额
        wallet.setFrozenAmount(wallet.getFrozenAmount().subtract(uro.getAmount()));
        //重置提现总额
        wallet.setWithdrawAmount(wallet.getWithdrawAmount().add(uro.getAmount()));
        walletMapper.update(wallet);
    }

    @Override
    public Wallet recharge(WalletRechargeRO ro) {
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




        return null;
    }

    @Override
    public Wallet queryByGuid(String guid) {
        return walletMapper.queryByGuid(guid);
    }

}
