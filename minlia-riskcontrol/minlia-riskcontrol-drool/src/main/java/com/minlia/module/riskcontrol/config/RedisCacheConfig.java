//package com.minlia.module.riskcontrol.config;
//
//import com.minlia.module.riskcontrol.listener.RiskBlackListPublishListener;
//import com.minlia.module.riskcontrol.listener.RiskDroolsPublishListener;
//import com.minlia.module.riskcontrol.listener.RiskIpListPublishListener;
//import com.minlia.module.riskcontrol.listener.RiskUrlListPublishListener;
//import com.minlia.module.riskcontrol.service.RiskListService;
//import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
//import com.minlia.module.riskcontrol.service.RiskDroolsConfigService;
//import com.minlia.module.riskcontrol.service.RiskIpListService;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//
//@Configuration
//@EnableCaching
//public class RedisCacheConfig {
//
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter ipListenerAdapter,
//                                            MessageListenerAdapter urlListenerAdapter,
//                                            MessageListenerAdapter blackListenerAdapter,
//                                            MessageListenerAdapter droolsListenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        // 可以添加多个 messageListener，配置不同的交换机
//        container.addMessageListener(ipListenerAdapter, new PatternTopic(RiskIpListService.channel));
//        container.addMessageListener(urlListenerAdapter, new PatternTopic(RiskBlackUrlService.channel));
//        container.addMessageListener(blackListenerAdapter, new PatternTopic(RiskListService.channel));
//        container.addMessageListener(droolsListenerAdapter, new PatternTopic(RiskDroolsConfigService.channel));
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter ipListenerAdapter(RiskIpListPublishListener receiver) {
//        return new MessageListenerAdapter(receiver, "onMessage");
//    }
//
//    @Bean
//    MessageListenerAdapter urlListenerAdapter(RiskUrlListPublishListener receiver) {
//        return new MessageListenerAdapter(receiver, "onMessage");
//    }
//
//    @Bean
//    MessageListenerAdapter blackListenerAdapter(RiskBlackListPublishListener receiver) {
//        return new MessageListenerAdapter(receiver, "onMessage");
//    }
//
//    @Bean
//    MessageListenerAdapter droolsListenerAdapter(RiskDroolsPublishListener receiver) {
//        return new MessageListenerAdapter(receiver, "onMessage");
//    }
//
//}