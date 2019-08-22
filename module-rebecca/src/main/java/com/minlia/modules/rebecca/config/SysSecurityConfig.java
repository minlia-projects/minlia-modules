package com.minlia.modules.rebecca.config;

import com.minlia.module.bible.annotation.BibleAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 安全配置
 * @date 2019/8/22 4:48 PM
 */
@BibleAutowired(type = "SYSTEM_SECURITY_CONFIG")
@Component
@Data
public class SysSecurityConfig {

    /**
     * 最大校验失败次数
     */
    private Integer maxValidationFailureTimes = 3;

    /**
     * 账号有效天数
     */
    private Integer accountEffectiveDays = 365;

    /**
     * 凭证有效天数
     */
    private Integer credentialsEffectiveDays = 90;

    /**
     * 锁定天数
     */
    private Integer lockedDays = 1;

}
