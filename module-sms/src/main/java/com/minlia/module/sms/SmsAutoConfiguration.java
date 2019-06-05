package com.minlia.module.sms;

import com.minlia.module.sms.property.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by garen on 6/21/17.
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties(value = {SmsProperties.class})
public class SmsAutoConfiguration {

}
