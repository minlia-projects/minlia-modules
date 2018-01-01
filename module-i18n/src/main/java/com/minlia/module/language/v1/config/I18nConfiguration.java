package com.minlia.module.language.v1.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = I18nProperties.class)
@Configuration
public class I18nConfiguration {

}
