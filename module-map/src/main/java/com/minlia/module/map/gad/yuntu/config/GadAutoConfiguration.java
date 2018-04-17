package com.minlia.module.map.gad.yuntu.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.map.gad.constants.GadBibleConstants;
import com.minlia.module.map.gad.yuntu.service.GadYuntuService;
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
        yuntuConfig.setWebApiKey(bibleItemService.get(GadBibleConstants.GAD, GadBibleConstants.WEB_API_KEY));
        yuntuConfig.setTableId(bibleItemService.get(GadBibleConstants.GAD, GadBibleConstants.YUNTU_TABLE_ID));
        return yuntuConfig;
    }

}
