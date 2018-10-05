package com.minlia.module.gad;

import com.minlia.module.gad.yuntu.service.GadYuntuService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(GadYuntuService.class)
public class GadAutoConfiguration {

//    @Autowired
//    private BibleItemService bibleItemService;

//    @Lazy
//    @Bean
//    public GadYuntuConfig yuntuConfig() {
//        GadYuntuConfig yuntuConfig = new GadYuntuConfig();
//        yuntuConfig.setWebApiKey(bibleItemService.get(GadYuntuBibleConstants.GAD, GadYuntuBibleConstants.WEB_API_KEY));
//        yuntuConfig.setTableId(bibleItemService.get(GadYuntuBibleConstants.GAD, GadYuntuBibleConstants.YUNTU_TABLE_ID));
//        return yuntuConfig;
//    }

}
