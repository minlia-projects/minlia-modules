package com.minlia.module.wallet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WithdrawStatusEnum {

    PENDING(0, "PENDING"),

    SETTLED(1, "SETTLED"),

    REJECTED(2, "REJECTED");

    private final int value;

    private final String code;

}