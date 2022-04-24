package com.minlia.module.riskcontrol.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 时间区间枚举
 */
@Getter
@AllArgsConstructor
public enum TimePeriodEnum {

    ALL(0, "ALL"),

    /**
     * 最后一分钟
     */
    LAST_MIN(1, "LAST_MIN"),

    /**
     * 最后一刻钟
     */
    LAST_QUARTER(2, "LAST_QUARTER"),

    /**
     * 最后一小时
     */
    LAST_HOUR(3, "LAST_HOUR"),

    /**
     * 最后一天
     */
    LAST_DAY(4, "LAST_DAY");

    private Integer value;

    @EnumValue
    private String code;

    public LocalDateTime getMinTime(LocalDateTime now) {
        if (this.equals(ALL)) {
            return LocalDateTime.MIN;
        } else {
            return now.minusSeconds(getTimeDiff());
        }
    }

    public LocalDateTime getMaxTime(LocalDateTime now) {
        return now;
    }

    public long getTimeDiff() {
        long timeDiff;
        switch (this) {
            case ALL:
                timeDiff = Long.MAX_VALUE;
                break;
            case LAST_MIN:
                timeDiff = 60;
                break;
            case LAST_QUARTER:
                timeDiff = 15 * 60;
                break;
            case LAST_HOUR:
                timeDiff = 3600;
                break;
            case LAST_DAY:
                timeDiff = 24 * 3600;
                break;
            default:
                timeDiff = 60;
                break;
        }
        return timeDiff;
    }

}
