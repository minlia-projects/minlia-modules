package com.minlia.module.captcha;

import com.minlia.module.captcha.config.CaptchaConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 6/21/17.
 */
@Configuration
@EnableConfigurationProperties({CaptchaConfig.class})
public class CaptchaAutoConfiguration {

}
