package com.minlia.module.wallet.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@ConfigAutowired(type = "SYS_WALLET_CONFIG")
public class WalletConfig {

    /**
     * 最小提现金额
     */
    private BigDecimal minimumWithdrawalAmount;

}