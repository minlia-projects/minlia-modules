//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.module.wallet.v1.bean.WithdrawApplyRequestBody;
//import com.minlia.module.wallet.v1.bean.WithdrawApprovalRequestBody;
//import com.minlia.module.wallet.v1.domain.WithdrawApply;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//
//
///**
// * 提现账号接口(银行账号)
// * Created by user on 3/16/17.
// */
//@Transactional
//public interface WithdrawApplyService {
//
//    /**
//     * 申请
//     * @param requestBody
//     * @return
//     */
//    WithdrawApply apply(WithdrawApplyRequestBody requestBody);
//
//    /**
//     * 提现完成确认
//     * 更新钱包总额、冻结金额
//     * @param requestBody
//     * @return
//     */
//    WithdrawApply approval(WithdrawApprovalRequestBody requestBody);
//
//    /**
//     * 获取余额
//     * @return
//     */
//    BigDecimal getBalance();
//
//    /**
//     * 检查提现余额是否足够
//     * @param withdrawAmount
//     * @return
//     */
//    Boolean verifyBalanceEnough(BigDecimal withdrawAmount);
//
//    /**
//     * 核实提现前置条件
//     * @param withdrawAmount
//     * @return
//     */
//    Boolean verifyPrecondition(BigDecimal withdrawAmount);
//
//    /**
//     * 获取提现前置条件
//     * @return
//     */
//    BigDecimal getWithdrawPrecondition();
//
//}
