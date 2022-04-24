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

    BLACK(0, "CHANGE_CELLPHONE"),

    WHITE(1, "CHANGE_CELLPHONE"),

    GRAY(2, "CHANGE_CELLPHONE");

    private Integer value;

    @EnumValue
    private String desc;

}