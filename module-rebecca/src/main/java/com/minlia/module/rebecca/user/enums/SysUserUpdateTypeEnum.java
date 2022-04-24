package com.minlia.module.rebecca.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author garen
 * @version 1.0
 * @description 用户信息更新类型
 * @date 2019/7/3 4:37 PM
 */
@Getter
@AllArgsConstructor
public enum SysUserUpdateTypeEnum {

    CHANGE_CELLPHONE(0, "CHANGE_CELLPHONE"),

    CHANGE_EMAIL(1, "CHANGE_EMAIL"),

    CHANGE_USERNAME(2, "CHANGE_USERNAME"),

    CHANGE_PASSWORD(3, "CHANGE_PASSWORD"),

    CHANGE_NICKNAME(4, "CHANGE_NICKNAME"),

    PASSWORD_ERROR(5, "PASSWORD_ERROR"),

    SYSTEM_UPDATE(6, "SYSTEM_UPDATE");

    private Integer value;

    @EnumValue
    private String code;

}