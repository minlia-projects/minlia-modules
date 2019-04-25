package com.minlia.module.wallet.enumeration;

/**
 * 区分钱包交易类型
 */
public enum WalletOperationTypeEnum {

    /**
     * 收款
     */
    RECEIVABLES,

    /**
     * 付款
     */
    PAY,

    /**
     * 取消付款
     */
    CANCEL_PAY,

    /**
     * 充值
     */
    RECHARGE,

    /**
     * 提现，非申请
     */
    WITHDRAW,

    /**
     * 提现申请
     */
    WITHDRAW_APPLY,

    /**
     * 提现结算
     */
    WITHDRAW_SETTLED,

    /**
     * 退款
     */
    REFUND

}
