package com.minlia.cloud.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.ZonedDateTime;


@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@Slf4j
/**
 * Minlia Data Auto Configuration
 */
@EnableTransactionManagement
public class CloudDataJpaAutoConfiguration {


    /**
     * JPA Configuration
     */
    @EntityScan(basePackages = {".**.domain", ".**.model","org.springframework.data.jpa.convert.threeten"})//,basePackageClasses = Jsr310JpaConverters.class
    @EnableJpaRepositories(value = {".**.repository"}, considerNestedRepositories = true, transactionManagerRef = "jpaTransactionManager")
    @EnableJpaAuditing
    @Configuration
    @ConditionalOnClass(CloudDataJpaAutoConfiguration.class)
    public static class EnableMinliaCloudJpaRepository {
        @Autowired
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

        @Primary
        @Bean
        public JpaTransactionManager jpaTransactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
            return transactionManager;
        }



    }


}