package com.minlia.module.common.enumeration;

public enum GenderEnum {

    MALE("男"),

    FEMALE("女"),

    UNKNOWN("未知");

    GenderEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}