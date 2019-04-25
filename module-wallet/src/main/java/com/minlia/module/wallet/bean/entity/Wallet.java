package com.minlia.module.wallet.bean.entity;


import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 钱包
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet extends AbstractEntity {

    /**
     * 用户ID
     */
    private String guid;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 余额
     */
    private BigDecimal balanceAmount;

    /**
     * 总提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 总流水
     */
    private BigDecimal flowAmount;

}
