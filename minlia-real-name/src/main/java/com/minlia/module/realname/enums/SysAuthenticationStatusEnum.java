package com.minlia.module.realname.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证状态枚举
 *
 * @author garen
 */
@Getter
@AllArgsConstructor
public enum SysAuthenticationStatusEnum {

    /**
     * 待定
     */
    PENDING(0, "CELLPHONE"),

    /**
     * 通过
     */
    PASSED(1, "CELLPHONE"),

    /**
     * 不通过
     */
    FAILED(2, "CELLPHONE");

    @EnumValue
    private Integer value;

    private String desc;

}