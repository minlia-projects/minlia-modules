package com.minlia.modules.security.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author garen
 * @date 2018/1/31
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 用户名
     */
    USERNAME(0, "USERNAME"),

    /**
     * 手机号码
     */
    CELLPHONE(1, "USERNAME"),

    /**
     * 邮箱
     */
    EMAIL(2, "USERNAME");

    private Integer value;

    @EnumValue
    private String name;

}