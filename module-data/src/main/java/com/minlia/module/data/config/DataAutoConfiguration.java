package com.minlia.module.data.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @ConditionalOnMissingBean(value = PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        //https://github.com/baomidou/mybatis-plus-samples/blob/master/mybatis-plus-sample-pagination/src/main/java/com/baomidou/mybatisplus/samples/pagination/config/MybatisPlusConfig.java
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }

}