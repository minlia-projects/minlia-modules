package com.minlia.module.email.config;

import com.minlia.module.bible.annotation.BibleAutowired;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author garen
 * @version 1.0
 * @description 邮件配置
 * @date 2019/8/22 3:05 PM
 */
@Data
@Component
@BibleAutowired
public class EmailConfig {

    private boolean realSwitchFlag;

}