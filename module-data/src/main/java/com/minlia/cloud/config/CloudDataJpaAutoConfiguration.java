package com.minlia.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@Slf4j
/**
 * Minlia Data Auto Configuration
 */

@EnableTransactionManagement
public class CloudDataJpaAutoConfiguration {

//    @Bean
//    public MybatisPageableInterceptor mybatisPageableInterceptor(){
//        MybatisPageableInterceptor mybatisPageableInterceptor= new MybatisPageableInterceptor();
//        mybatisPageableInterceptor.setDialect(new MySql5Dialect());
//        return mybatisPageableInterceptor;
//    }
//    @Bean
//    @Lazy
//    public MapperLoader mapperLoader(){
//        MapperLoader mapperLoader= new MapperLoader();
//        return mapperLoader;
//    }

//    @Bean
//    public PaginationStatementInterceptor paginationStatementInterceptor(){
//        return new PaginationStatementInterceptor();
//    }

    /**
     * JPA Configuration
     */
    @EntityScan(basePackages = {".**.domain", ".**.model"})
//    @EnableJpaRepositories(value = {".**.repository"}, considerNestedRepositories = true, transactionManagerRef = "jpaTransactionManager")
//    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Configuration
    @ConditionalOnClass(CloudDataJpaAutoConfiguration.class)

    public static class EnableMinliaCloudJpaRepository {
//
//        @Autowired
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
//
//        @Primary
//        @Bean
//        public JpaTransactionManager jpaTransactionManager() {
//            JpaTransactionManager transactionManager = new JpaTransactionManager();
//            transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
//            return transactionManager;
//        }
    }


}