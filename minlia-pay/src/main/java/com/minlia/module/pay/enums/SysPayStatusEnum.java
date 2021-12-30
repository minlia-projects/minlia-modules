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

    UNPAID(0, "APP"),

    PAID(1, "APP"),

    CANCELED(2, "APP");

    private final Integer value;

    @EnumValue
    private final String code;

}