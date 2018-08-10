package com.minlia.module.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
@Data
@ConfigurationProperties(prefix = "sms", ignoreUnknownFields = false)
public class CaptchaConfig {

    /**
     * 每天发送次数
     */
    private Integer dayTimes;

    private Map<String,String> templates;

}
