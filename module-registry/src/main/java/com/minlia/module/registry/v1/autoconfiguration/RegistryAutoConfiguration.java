package com.minlia.module.registry.v1.autoconfiguration;

import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 6/19/17.
 * 启动时延迟此BEAN初始化
 */
@Configuration
public class RegistryAutoConfiguration {


//    @Configuration
//    @Import(LocaleConfiguration.class)
//    @ConditionalOnMissingBean(LocaleConfiguration.class)
//    public static class MinliaLocaleConfiguration implements EnvironmentAware {
//
//
//        private RelaxedPropertyResolver propertyResolver;
//
//
//        @Override
//        public void setEnvironment(Environment environment) {
//            this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.application.");
//        }
//    }

}
