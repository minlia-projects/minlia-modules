package com.minlia.cloud.data.batis.support.config;

import com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mybatis.domains.AuditDateAware;
import org.springframework.data.mybatis.replication.transaction.ReadWriteManagedTransactionFactory;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Date;


@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@Slf4j
/**
 * Minlia Data Batis Auto Configuration
 */
public class MinliaDataBatisAutoConfiguration {

    /**
     * Batis Configuration
     */
    @EntityScan(basePackages = {".**.domain", ".**.model"})
    @Configuration
    @EnableMybatisRepositories(
            value = {".**.dao"},
            mapperLocations = "classpath*:/mappings/modules/*/*Dao.xml"
    )
    @EnableTransactionManagement
    public static class EnableMinliaDataBatisAutoConfiguration implements ResourceLoaderAware {

//    private final Logger log = LoggerFactory.getLogger(DatabaseAutoConfiguration.class);

        private ResourceLoader resourceLoader;

        @Bean
        public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setTransactionFactory(new ReadWriteManagedTransactionFactory());
            return factoryBean;
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
//    @Bean
//    public AuditorAware<Long> auditorAware() {
//        return new AuditorAware<Long>() {
//            @Override
//            public Long getCurrentAuditor() {
//                return 1001L;
//            }
//        };
//    }


        @Bean
        @ConditionalOnMissingBean
        public AuditDateAware<Date> auditDateAware() {
            return new AuditDateAware<Date>() {
                @Override
                public Date getCurrentDate() {
                    return new Date();
                }
            };
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }










    /**
     * JPA Configuration
     */
    @org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = {".**.domain", ".**.model"})
//, basePackageClasses = {Jsr310JpaConverters.class}
//    @EnableJpaRepositories(value = {".**.repository"}, considerNestedRepositories = true)
//    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Configuration
    @ConditionalOnClass(MinliaDataBatisAutoConfiguration.class)
    public static class EnableMinliaCloudJpaRepository {}






}