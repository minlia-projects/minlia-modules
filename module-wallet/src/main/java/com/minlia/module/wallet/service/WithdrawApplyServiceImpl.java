//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.boot.utils.ApiPreconditions;
//import com.minlia.boot.v1.code.ApiCode;
//import com.minlia.module.bible.v1.domain.BibleItem;
//import com.minlia.module.bible.v1.service.BibleItemService;
//import com.minlia.module.security.v1.domain.User;
//import com.minlia.module.security.v1.service.UserQueryService;
//import com.minlia.module.security.v1.utils.SecurityUtils;
//import com.minlia.module.wallet.v1.body.WalletRequestBody;
//import com.minlia.module.wallet.v1.body.WithdrawApplyRequestBody;
//import com.minlia.module.wallet.v1.body.WithdrawApprovalRequestBody;
//import com.minlia.module.wallet.v1.domain.BankCardDo;
//import com.minlia.module.wallet.v1.domain.Wallet;
//import com.minlia.module.wallet.v1.domain.WithdrawApply;
//import com.minlia.module.wallet.v1.enumeration.WalletOperationTypeEnum;
//import com.minlia.module.wallet.v1.enumeration.WithdrawStatusEnum;
//import com.minlia.module.wallet.v1.mapper.WithdrawApplyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
///**
// * Created by user on 3/13/17.
// */
//@Service
//public class WithdrawApplyServiceImpl implements WithdrawApplyService {
//
//    @Autowired
//    private WalletService walletService;
//    @Autowired
//    private BankCardService bankCardService;
//    @Autowired
//    private UserQueryService userQueryService;
//    @Autowired
//    private BibleItemService bibleItemService;
//    @Autowired
//    private WithdrawApplyRepository repository;
//
//    @Override
//    public synchronized WithdrawApply apply(WithdrawApplyRequestBody requestBody) {
//        User user = SecurityUtils.getCurrentUser();
//        BankCardDo bankCard = bankCardService.findByUserIdAndIsWithdrawIsTrue(user.getId());
////        ApiPreconditions.is(null == bankCard, ApiCode.NOT_FOUND,"未设置提现银行卡");
//        ApiPreconditions.not(this.verifyBalanceEnough(requestBody.getAmount()), ApiCode.NOT_FOUND,"余额不足");
//        BigDecimal limitAmount = getWithdrawPrecondition();
//        ApiPreconditions.is(limitAmount.compareTo(requestBody.getAmount()) == 1, ApiCode.NOT_FOUND,"最小提现金额不能小于" + limitAmount);
//
//        //插入数据
//        WithdrawApply withdrawApply = WithdrawApply.builder()
//                .userId(user.getId())
//                .applyAmount(requestBody.getAmount())
//                .settledAmount(requestBody.getAmount())
//                .note(requestBody.getNote())
//                .withdrawStatus(WithdrawStatusEnum.apply)
//                .build();
//        if (null != bankCard) {
//            withdrawApply.setCardholder(bankCard.getCardholder());
//            withdrawApply.setBankName(bankCard.getBankName());
//            withdrawApply.setCardNumber(bankCard.getCardNumber());
//        }
//
//        repository.save(withdrawApply);
//
//        //更新钱包冻结金额,可用余额
//        WalletRequestBody walletRequestBody = WalletRequestBody.builder()
//                .userId(user.getId())
//                .amount(requestBody.getAmount())
//                .walletOperationType(WalletOperationTypeEnum.WITHDRAW_APPLY)
//                .note(requestBody.getNote())
//                .build();
//        walletService.update(walletRequestBody);
//
//        //判断是否秒批 TODO
//        if (false) {
//            this.approval(WithdrawApprovalRequestBody.builder().id(withdrawApply.getId()).settlementAmount(withdrawApply.getApplyAmount()).note(withdrawApply.getNote()).build());
//        }
//
//        return withdrawApply;
//    }
//
//    @Override
//    public synchronized WithdrawApply approval(WithdrawApprovalRequestBody requestBody) {
//        //先不考虑驳回 TODO
//        WithdrawApply withdrawApply = repository.findOne(requestBody.getId());
//        ApiPreconditions.is(null == withdrawApply,ApiCode.NOT_FOUND,"提现记录不存在");
//        ApiPreconditions.is(withdrawApply.getWithdrawStatus().equals(WithdrawStatusEnum.settled),ApiCode.NOT_AUTHORIZED,"已结算");
//        ApiPreconditions.is(withdrawApply.getWithdrawStatus().equals(WithdrawStatusEnum.reject),ApiCode.NOT_AUTHORIZED,"已驳回");
//        User user = userQueryService.findOne(withdrawApply.getUserId());
//
//        //更新状态为完成
//        withdrawApply.setWithdrawStatus(WithdrawStatusEnum.settled);
//        withdrawApply.setSettledAmount(withdrawApply.getApplyAmount());
//        repository.save(withdrawApply);
//
//        //更新钱包总额及冻结金额
//        WalletRequestBody walletRequestBody = WalletRequestBody.builder()
//                .userId(user.getId())
//                .amount(withdrawApply.getApplyAmount())
//                .walletOperationType(WalletOperationTypeEnum.WITHDRAW_SETTLED)
//                .note(withdrawApply.getNote())
//                .build();
//        walletService.update(walletRequestBody);
//
//        //TODO 调用第三方代付接口
//
//        return withdrawApply;
//    }
//
//    @Override
//    public BigDecimal getBalance() {
//        Wallet wallet = walletService.findOneByOwner();
//        return wallet.getBalance();
//    }
//
//    @Override
//    public Boolean verifyBalanceEnough(BigDecimal withdrawalAmount) {
//        if (withdrawalAmount.compareTo(this.getBalance()) == 1) {
//            return Boolean.FALSE;
//        } else {
//            return Boolean.TRUE;
//        }
//    }
//
//    @Override
//    public Boolean verifyPrecondition(BigDecimal withdrawAmount) {
//        BigDecimal limitAmount = this.getWithdrawPrecondition();
//
//        //是否大于或等于最小提现金额
//        if (withdrawAmount.compareTo(limitAmount) != -1) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public BigDecimal getWithdrawPrecondition() {
//        //或者累计总金额>=5000
//        BibleItem bibleItem = bibleItemService.findByBible_CodeAndCode("WITHDRAW","WITHDRAW_BALANCE_LOWER_LIMIT");
//        ApiPreconditions.is(null == bibleItem, ApiCode.NOT_FOUND,"最小提现金额未配置");
//        return new BigDecimal(bibleItem.getLabel());
//    }
//
//}
