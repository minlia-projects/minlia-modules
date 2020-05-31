package com.minlia.module.data.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Minlia Data Auto Configuration
 */

@ComponentScan(basePackages = "com.minlia.*")
@MapperScan(basePackages={"com.minlia.**.mapper"})
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@EnableTransactionManagement
public class DataAutoConfiguration {

}