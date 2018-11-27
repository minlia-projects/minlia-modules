//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.boot.v1.service.AbstractService;
//import com.minlia.module.security.v1.utils.SecurityUtils;
//import com.minlia.module.wallet.v1.bean.WalletRequestBody;
//import com.minlia.module.wallet.v1.domain.Wallet;
//import com.minlia.module.wallet.v1.mapper.WalletRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
///**
// * Created by user on 3/13/17.
// */
//@Service
//public class WalletServiceImpl extends AbstractService<Wallet> implements WalletService {
//
//    @Autowired
//    private WalletRepository repository;
//
//    @Override
//    protected JpaRepository<Wallet, Long> getRepository() {
//        return repository;
//    }
//
//    @Autowired
//    private WalletHistoryService walletHistoryService;
//
//    @Override
//    public Wallet init(Long userId) {
//        Wallet wallet = repository.findByUserId(userId);
//        if (null == wallet) {
//            wallet = Wallet.builder()
//                    .userId(userId)
//                    .totalAmount(BigDecimal.ZERO)
//                    .flowAmount(BigDecimal.ZERO)
//                    .balance(BigDecimal.ZERO)
//                    .frozenAmount(BigDecimal.ZERO)
//                    .totalWithdrawAmount(BigDecimal.ZERO)
//                    .build();
//            repository.save(wallet);
//        }
//        return wallet;
//    }
//
//    public Wallet update(WalletRequestBody requestBody) {
//        Wallet wallet = repository.findByUserId(requestBody.getUserId());
//        switch (requestBody.getWalletOperationType()) {
//            case RECEIVABLES:
//                this.receipts(requestBody,wallet);
//                break;
//            case PAY:
//                this.pay(requestBody,wallet);
//                break;
//            case RECHARGE:
//                this.recharge(requestBody,wallet);
//                break;
//            case REFUND:
//                this.refund(requestBody,wallet);
//                break;
//            case WITHDRAW_APPLY:
//                this.withdrawApply(requestBody,wallet);
//                break;
//            case WITHDRAW_SETTLED:
//                this.withdrawCompleted(requestBody,wallet);
//        }
//        this.walletHistoryService.create(requestBody,wallet.getId());
//        return wallet;
//    }
//
//    /**
//     * 收款
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    private void receipts(WalletRequestBody requestBody,Wallet wallet) {
//        wallet.setFlowAmount(wallet.getFlowAmount().add(requestBody.getAmount()));
//        wallet.setTotalAmount(wallet.getTotalAmount().add(requestBody.getAmount()));
//        wallet.setBalance(wallet.getBalance().add(requestBody.getAmount()));
//        super.update(wallet);
//    }
//
//    /**
//     * 付款
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    private void pay(WalletRequestBody requestBody,Wallet wallet) {
//        wallet.setTotalAmount(wallet.getTotalAmount().subtract(requestBody.getAmount()));
//        wallet.setBalance(wallet.getBalance().subtract(requestBody.getAmount()));
//        super.update(wallet);
//    }
//
//    /**
//     * 充值
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    private void recharge(WalletRequestBody requestBody,Wallet wallet) {
//        wallet.setFlowAmount(wallet.getFlowAmount().add(requestBody.getAmount()));
//        wallet.setTotalAmount(wallet.getTotalAmount().add(requestBody.getAmount()));
//        wallet.setBalance(wallet.getBalance().add(requestBody.getAmount()));
//        super.update(wallet);
//    }
//
//    /**
//     * 退款
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    public void refund(WalletRequestBody requestBody,Wallet wallet) {
//        wallet.setTotalAmount(wallet.getTotalAmount().subtract(requestBody.getAmount()));
//        wallet.setBalance(wallet.getBalance().subtract(requestBody.getAmount()));
//        this.update(wallet);
//    }
//
//    /**
//     * 提现申请
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    private void withdrawApply(WalletRequestBody requestBody,Wallet wallet) {
//        //重置冻结金额
//        wallet.setFrozenAmount(wallet.getFrozenAmount().add(requestBody.getAmount()));
//        //重置余额
//        wallet.setBalance(wallet.getBalance().subtract(requestBody.getAmount()));
//        super.update(wallet);
//    }
//
//    /**
//     * 提现完成
//     *
//     * @param requestBody
//     * @param wallet
//     */
//    private void withdrawCompleted(WalletRequestBody requestBody,Wallet wallet) {
//        //重置总额
//        wallet.setTotalAmount(wallet.getTotalAmount().subtract(requestBody.getAmount()));
//        //重置冻结金额
//        wallet.setFrozenAmount(wallet.getFrozenAmount().subtract(requestBody.getAmount()));
//        //重置提现总额
//        wallet.setTotalWithdrawAmount(wallet.getTotalWithdrawAmount().add(requestBody.getAmount()));
//        super.update(wallet);
//    }
//
//    @Override
//    public Wallet findOneByOwner() {
//        return repository.findByUserId(SecurityUtils.getCurrentUser().getId());
//    }
//
//}
