package com.minlia.module.data.enumeration;

/**
 * 数据状态枚举
 * 请勿随意调整枚举顺序, 可能导致其他业务系统状态紊乱
 */
public enum DataStatusEnum {


    /**
     * 无效状态
     */
    INVALID,
    /**
     * 正常状态
     */
    OK,

    /**
     * 已启用
     */
    ENABLED,

    /**
     * 已禁用
     */
    DISABLED,

    /**
     * 已删除
     */
    DELETED,

    /**
     * 已过期
     */
    EXPIRED,

    /**
     * 已归档
     */
    ARCHIVED


}