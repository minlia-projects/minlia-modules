package com.minlia.module.riskcontrol.enums;

/**
 * 风险等级枚举
 * @author garen
 * @version 1.0
 * @date 2019/6/28 5:55 PM
 */
public enum RiskLevelEnum {

    /**
     * 正常，没有风险
     */
    NORMAL,

    /**
     * 警告，超过事件评分阀值
     */
    WARNING,

    /**
     * 危险，超过事件最高评分
     */
    DANGER

}
