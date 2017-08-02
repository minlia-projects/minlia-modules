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
 * Minlia Data Batis Auto Configuration
 */
public class MinliaDataAutoConfiguration {

//    /**
//     * Batis Configuration
//     */
//    @EntityScan(basePackages = {".**.domain", ".**.model"})
//    @Configuration
//    @EnableMybatisRepositories(
////            repositoryFactoryBeanClass =
//
//            value = {".**.dao"},
//            mapperLocations = "classpath*:/mappings/modules/*/*Dao.xml"
//    )
//    @EnableTransactionManagement
//    public static class EnableMinliaDataBatisAutoConfiguration implements ResourceLoaderAware {
//
////    private final Logger log = LoggerFactory.getLogger(DatabaseAutoConfiguration.class);
//
//        private ResourceLoader resourceLoader;
//
//        @Bean
//        public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
//            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//            factoryBean.setDataSource(dataSource);
//            factoryBean.setTransactionFactory(new ReadWriteManagedTransactionFactory());
//            return factoryBean;
//        }
//
//        @Bean
//        public PlatformTransactionManager transactionManagerBatis(DataSource dataSource) {
//            return new DataSourceTransactionManager(dataSource);
//        }
//
//
////    @Bean
////    public AuditorAware<Long> auditorAware() {
////        return new AuditorAware<Long>() {
////            @Override
////            public Long getCurrentAuditor() {
////                return 1001L;
////            }
////        };
////    }
//
//
////        @Bean
////        @ConditionalOnMissingBean
////        public AuditDateAware<Date> auditDateAware() {
////            return new AuditDateAware<Date>() {
////                @Override
////                public Date getCurrentDate() {
////                    return new Date();
////                }
////            };
////        }
//
//        @Override
//        public void setResourceLoader(ResourceLoader resourceLoader) {
//            this.resourceLoader = resourceLoader;
//        }
//    }


    /**
     * JPA Configuration
     */
    @EntityScan(basePackages = {".**.domain", ".**.model"})
//, basePackageClasses = {Jsr310JpaConverters.class}
    @EnableJpaRepositories(value = {".**.repository"}, considerNestedRepositories = true,transactionManagerRef = "jpaTransactionManager")//
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