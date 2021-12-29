package com.minlia.module.i18n.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author garen
 * @date 2018/8/20
 */
@Getter
@AllArgsConstructor
public enum LocaleEnum {

    zh_CN(0, "zh_CN"),

    zh_TW(1, "zh_TW"),

    en_US(2, "en_US");

    private Integer value;

    @EnumValue
    private String code;

}