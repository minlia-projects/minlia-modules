package com.minlia.modules.rebecca;

import com.minlia.modules.rebecca.config.RebeccaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 8/14/17.
 */
@Configuration
@EnableConfigurationProperties(value = RebeccaProperties.class)
public class RbacAutoConfiguration {

}
