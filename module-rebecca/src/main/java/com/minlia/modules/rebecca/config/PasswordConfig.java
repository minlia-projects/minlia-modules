package com.minlia.modules.rebecca.config;

import com.minlia.module.bible.annotation.BibleAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 密码配置
 * @date 2019/6/17 11:47 AM
 */
@BibleAutowired(type = "PASSWORD_CONFIG")
@Component
@Data
public class PasswordConfig {

    /**
     * 最大校验失败次数
     */
    private Integer maxValidationFailureTimes;

}