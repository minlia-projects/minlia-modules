package com.minlia.module.email;

import com.minlia.module.email.property.AliyunEmailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by garen on 6/21/17.
 */
@Configuration
@EnableConfigurationProperties(value = {AliyunEmailProperties.class })
public class EmailAutoConfiguration {

}
