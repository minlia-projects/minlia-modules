package com.minlia.module.tenant.config;

import com.minlia.module.tenant.aspect.AppidAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppidAspectConfiguration {

    @Bean
    public AppidAspect loggingAspect() {
        return new AppidAspect();
    }
}
