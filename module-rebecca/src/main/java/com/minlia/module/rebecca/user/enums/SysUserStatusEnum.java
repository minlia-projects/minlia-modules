package com.minlia.module.rebecca.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author garen
 * @version 1.0
 * @description 用户状态
 * @date 2019/7/29 10:57 AM
 */
@Getter
@AllArgsConstructor
public enum SysUserStatusEnum {

    /**
     * 活动
     */
    ACTIVE(0, "ACTIVE"),

    /**
     * 不活动
     */
    INACTIVE(1, "INACTIVE"),

    /**
     * 终止
     */
    TERMINATED(2, "TERMINATED");

    private Integer value;

    @EnumValue
    private String code;

}
