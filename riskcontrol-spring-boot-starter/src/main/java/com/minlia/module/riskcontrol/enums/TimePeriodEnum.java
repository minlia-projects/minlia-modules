package com.minlia.module.riskcontrol.enums;

import java.util.Date;

/**
 * 时间区间枚举
 */
public enum TimePeriodEnum {

    ALL,

    /**
     * 最后一分钟
     */
    LAST_MIN,

    /**
     * 最后一刻钟
     */
    LAST_QUARTER,

    /**
     * 最后一小时
     */
    LAST_HOUR,

    /**
     * 最后一天
     */
    LAST_DAY;

    public Date getMinTime(Date now) {
        if (this.equals(ALL)) {
            return new Date(0);
        } else {
            return new Date(now.getTime() - getTimeDiff());
        }
    }

    public Date getMaxTime(Date now) {
        return now;
    }

    public long getTimeDiff() {
        long timeDiff;
        switch (this) {
            case ALL:
                timeDiff = Long.MAX_VALUE;
                break;
            case LAST_MIN:
                timeDiff = 60 * 1000L;
                break;
            case LAST_QUARTER:
                timeDiff = 15 * 60 * 1000L;
                break;
            case LAST_HOUR:
                timeDiff = 3600 * 1000L;
                break;
            case LAST_DAY:
                timeDiff = 24 * 3600 * 1000L;
                break;
            default:
                timeDiff = 60 * 1000L;
                break;
        }
        return timeDiff;
    }

}
