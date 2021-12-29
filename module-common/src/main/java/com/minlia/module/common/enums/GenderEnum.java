package com.minlia.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {

    MALE(0, "男"),

    FEMALE(1, "女"),

    UNKNOWN(2, "未知");

    private final Integer value;

    private final String code;

}