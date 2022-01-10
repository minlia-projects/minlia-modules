package com.minlia.module.wallet.constant;

import com.minlia.cloud.code.Code;

/**
 * @author garen
 * @date 2018/9/14
 */
public class WalletCode {

    public enum Message implements Code {

        /**
         * 未注册
         */
        UNREGISTERED,
        WITHDRAW_LOWER_LIMIT_NOT_CONFIG,
        BALANCE_NOT_ENOUGH,
        LESS_THAN_MINIMUM_WITHDRAWAL_AMOUNT, WITHDRAW_RECOED_NOT_EXISTS, WITHDRAW_STATUS_ERROR;

        @Override
        public String module() {
            return WalletConstant.MODULE_NAME;
        }
    }
}