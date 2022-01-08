package com.minlia.module.pay.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 状态
 */
@Getter
@RequiredArgsConstructor
public enum SysPayStatusEnum {

    UNPAID(0, "UNPAID"),

    PAID(1, "PAID"),

    CANCELED(2, "CANCELED"),

    REFUND(3, "REFUND");

    private final Integer value;

    @EnumValue
    private final String code;

}