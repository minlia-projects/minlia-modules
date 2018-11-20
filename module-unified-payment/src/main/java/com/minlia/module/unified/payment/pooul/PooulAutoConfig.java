//package com.minlia.module.unified.payment.pooul;
//
//import com.minlia.module.bible.service.BibleItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by garen on 2018/5/16.
// */
//@Configuration
//@ConditionalOnClass(PooulCreatePreOrderService.class)
//public class PooulAutoConfig {
//
//    @Autowired
//    private BibleItemService bibleItemService;
//
//    @Bean
////    @Lazy
//    @ConditionalOnMissingBean
//    public PooulConfig pooulConfig(){
//        PooulConfig pooulConfig = new PooulConfig();
//        pooulConfig.setAppId("wx469ffdb81de47e4d");
//        pooulConfig.setKey("6Uc2ACa4EpRuZe86fetUsPEcuspUWUcr");
//        return pooulConfig;
//    }
//
////    @Bean
////    @Lazy
////    @ConditionalOnMissingBean
////    public PooulCreatePreOrderService orderService(PooulConfig pooulConfig) {
////        WxMpService wxMpService = new WxMpServiceImpl();
////        wxMpService.setWxMpConfigStorage(configStorage);
////        return wxMpService;
////    }
//
//}
