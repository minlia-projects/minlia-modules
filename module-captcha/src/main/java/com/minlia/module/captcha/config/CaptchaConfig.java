package com.minlia.module.captcha.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/8/10.
 */
@ConfigAutowired(type = "SYS_CAPTCHA_CONFIG")
@Component
@Data
//@ConfigurationProperties(prefix = "sms", ignoreUnknownFields = false)
public class CaptchaConfig {

//    /**
//     * 每天发送次数
//     */
//    private Integer dayTimes = 0;
//
//    private Map<String,String> templates;

    private Boolean realSwitchFlag = true;

    private Boolean randomCodeFlag = true;

    /**
     * 大小
     */
    private Integer size;

    /**
     * 有效秒数
     */
    private Integer effectiveSeconds = 120;

    /**
     * 间隔秒数
     */
    private Integer intervalSeconds = 120;

    /**
     * 最大获取频率分钟：30分钟
     */
    private Integer maxFrequencyMinutes = 30;

    /**
     * 最大获取次数: 30分钟内12次
     */
    private Integer maxGetTimes = 12;

    /**
     * 最大校验错误的次数: 3次
     */
    private Integer maxValidationFailureTimes = 3;

    /**
     * 被锁定时长：30分钟
     */
    private Integer lockMinutes;

}
