package com.minlia.module.riskcontrol.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型枚举
 */
@Getter
@AllArgsConstructor
public enum RiskTypeEnum {

    BLACK(0, "BLACK"),

    WHITE(1, "WHITE"),

    GRAY(2, "GRAY");

    private Integer value;

    @EnumValue
    private String desc;

}