package com.minlia.module.wechat.miniapp.enumeration;

public enum Gender {
    NONE(0), MALE(1), FEMALE(2);
    private int i;

    Gender(int i) {
        this.i = i;
    }

    public static Gender valueOf(int i) {
        if (i == 1) {
            return MALE;
        } else if (i == 2) {
            return FEMALE;
        } else {
            return NONE;
        }
    }
    public int getValue() {
        return this.i;
    }
}