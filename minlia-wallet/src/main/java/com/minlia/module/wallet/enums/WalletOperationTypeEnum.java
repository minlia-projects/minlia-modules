package com.minlia.module.wallet.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 区分钱包交易类型
 */
@Getter
@AllArgsConstructor
public enum WalletOperationTypeEnum {

    /**
     * 进
     */
    IN(0, "IN"),

    /**
     * 出
     */
    OUT(1, "OUT"),

    /**
     * 冻结
     */
    FREEZE(2, "FREEZE"),

    /**
     * 解冻
     */
    THAW(3, "THAW"),

    /**
     * 冻结-结算
     */
    FREEZE_SETTLED(4, "FREEZE_SETTLED");

    @EnumValue
    private final int value;

    private final String code;

}