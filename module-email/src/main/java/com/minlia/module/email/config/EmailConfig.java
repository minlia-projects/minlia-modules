package com.minlia.module.email.config;

import com.minlia.module.common.annotation.ConfigAutowired;
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
@ConfigAutowired(type = "SYS_EMAIL_CONFIG")
public class EmailConfig {

    private Boolean realSwitchFlag = false;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String protocol = "smtp";

    private String replyTo;

}