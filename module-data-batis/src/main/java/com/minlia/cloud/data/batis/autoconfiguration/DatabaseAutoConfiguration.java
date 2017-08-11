package com.minlia.cloud.data.batis.autoconfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mybatis.domains.AuditDateAware;
import org.springframework.data.mybatis.replication.transaction.ReadWriteManagedTransactionFactory;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Date;


@Configuration

@EntityScan(basePackages = {".**.domain", ".**.model"})
@EnableMybatisRepositories(value = {".**.dao"}, mapperLocations = {"classpath:**/dao/*Dao.xml"}, transactionManagerRef = "batisTransactionManager")
public class DatabaseAutoConfiguration implements ResourceLoaderAware {

    private final Logger log = LoggerFactory.getLogger(DatabaseAutoConfiguration.class);

    private ResourceLoader resourceLoader;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ReadWriteManagedTransactionFactory factory = new ReadWriteManagedTransactionFactory();
        factoryBean.setTransactionFactory(factory);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager batisTransactionManager(DataSource dataSource) {
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
