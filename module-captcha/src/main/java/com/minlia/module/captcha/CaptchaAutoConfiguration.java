package com.minlia.module.captcha;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.util.BibleMapUtils;
import com.minlia.module.captcha.config.CaptchaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by will on 6/21/17.
 */
@Configuration
//@EnableConfigurationProperties({CaptchaConfig.class})
public class CaptchaAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
    public CaptchaConfig captchaConfig() {
        Map<String, String> map = bibleItemService.queryValueMap("CAPTCHA_CONFIG");
        return BibleMapUtils.mapToBean(map, CaptchaConfig.class);
    }

}
