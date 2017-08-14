//package com.minlia.modules.starter.permission.collector.autoconfiguration;
//
//import com.minlia.modules.starter.permission.collector.PermissionCollectorListener;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by qianyi on 2017/8/14.
// */
//@Configuration
//@ConditionalOnClass(PermissionCollectorAutoConfiguration.class)
//public class PermissionCollectorAutoConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean(PermissionCollectorListener.class)
//    public PermissionCollectorListener permissionCollectorListener(){
//        return new PermissionCollectorListener();
//    }
//
//}
