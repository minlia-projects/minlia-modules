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
     * 提现开关：0-关闭，1-打开
     */
    private Boolean withdrawOpen;

    /**
     * 最小提现金额，保留2位小数
     */
    private BigDecimal minimumWithdrawalAmount;

    /**
     * 提现服务费百分比
     */
    private Integer withdrawFee;

    /**
     * 提现类型：T0,T1.....
     */
    private Integer withdrawType;

}