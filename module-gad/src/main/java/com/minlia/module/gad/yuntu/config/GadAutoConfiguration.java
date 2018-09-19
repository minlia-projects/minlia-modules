package com.minlia.module.gad.yuntu.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.gad.yuntu.constant.GadYuntuBibleConstants;
import com.minlia.module.gad.yuntu.service.GadYuntuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ConditionalOnClass(GadYuntuService.class)
public class GadAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Lazy
    @Bean
    public GadYuntuConfig yuntuConfig() {
        GadYuntuConfig yuntuConfig = new GadYuntuConfig();
        yuntuConfig.setWebApiKey(bibleItemService.get(GadYuntuBibleConstants.GAD, GadYuntuBibleConstants.WEB_API_KEY));
        yuntuConfig.setTableId(bibleItemService.get(GadYuntuBibleConstants.GAD, GadYuntuBibleConstants.YUNTU_TABLE_ID));
        return yuntuConfig;
    }

}
