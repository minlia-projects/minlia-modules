package com.minlia.module.data.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Minlia Data Auto Configuration
 */
@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(basePackages={"com.minlia.*.mapper","com.minlia.*.dao"})
public class DataAutoConfiguration {

}