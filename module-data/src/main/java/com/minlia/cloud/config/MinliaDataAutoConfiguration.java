package com.minlia.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@Slf4j
/**
 * Minlia Data Auto Configuration
 */
public class MinliaDataAutoConfiguration {


    /**
     * JPA Configuration
     */
    @EntityScan(basePackages = {".**.domain", ".**.model"})
    @EnableJpaRepositories(value = {".**.repository"}, considerNestedRepositories = true, transactionManagerRef = "jpaTransactionManager")
//    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Configuration
    @ConditionalOnClass(MinliaDataAutoConfiguration.class)
    @EnableTransactionManagement
    public static class EnableMinliaCloudJpaRepository {

        @Autowired
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

        @Bean
        public JpaTransactionManager jpaTransactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
            return transactionManager;
        }


    }


}