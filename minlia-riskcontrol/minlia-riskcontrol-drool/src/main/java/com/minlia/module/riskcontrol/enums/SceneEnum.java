package com.minlia.module.riskcontrol.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by on 2016/8/6.
 */
@Getter
@AllArgsConstructor
public enum SceneEnum {

    /**
     * 登陆
     */
    LOGIN(0, "LOGIN"),

    /**
     * 注册
     */
    REGISTER(1, "REGISTER"),

    ORDER(2, "ORDER");

    private Integer value;

    @EnumValue
    private String code;

}