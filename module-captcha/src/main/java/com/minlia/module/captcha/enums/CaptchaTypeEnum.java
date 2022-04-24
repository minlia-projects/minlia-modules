package com.minlia.module.captcha.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author garen
 * @date 2018/10/24
 */
@Getter
@AllArgsConstructor
public enum CaptchaTypeEnum {

    CELLPHONE(0, "CELLPHONE"),

    EMAIL(1, "EMAIL");

    private Integer value;

    @EnumValue
    private String code;

}