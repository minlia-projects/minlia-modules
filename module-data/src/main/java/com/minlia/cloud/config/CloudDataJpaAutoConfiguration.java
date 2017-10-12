package com.minlia.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Minlia Data Auto Configuration
 */
@ComponentScan(basePackages = "com.minlia.*")
@Configuration
@ConditionalOnClass(com.minlia.cloud.autoconfiguration.MinliaCloudAutoConfiguration.class)
@Slf4j
@EnableTransactionManagement
public class CloudDataJpaAutoConfiguration {


  /**
   * JPA Configuration
   */
  @EntityScan(basePackages = {".**.domain", ".**.model",
      "org.springframework.data.jpa.convert.threeten"})
//,basePackageClasses = Jsr310JpaConverters.class
  @EnableJpaRepositories(value = {
      ".**.repository"}, considerNestedRepositories = true)//, transactionManagerRef = "jpaTransactionManager"
  @EnableJpaAuditing
  @Configuration
  @ConditionalOnClass(CloudDataJpaAutoConfiguration.class)
  public static class EnableMinliaCloudJpaRepository {

//    @Autowired
//    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
//
//    @Primary
//    @Bean
//    public JpaTransactionManager jpaTransactionManager() {
//      JpaTransactionManager transactionManager = new JpaTransactionManager();
//      transactionManager
//          .setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
//      return transactionManager;
//    }
//  }



//    @Autowired
//    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

//    @Primary
//    @Bean
//    public HibernateTransactionManager jpaTransactionManager() {
//      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//      return transactionManager;
//    }
  }

  //从自动装配改变为手动装配
//  @Autowired
//  private DataSource dataSource;



//  @Bean
//  @Profile("production")
//  public LiquibaseProperties liquibaseProperties() {
//    return new LiquibaseProperties();
//  }
//
//  @Bean
//  @Profile("production")
//  @DependsOn(value = "entityManagerFactory")
//  public SpringLiquibase liquibase() {
//    LiquibaseProperties liquibaseProperties = liquibaseProperties();
//    SpringLiquibase liquibase = new SpringLiquibase();
//    liquibase.setChangeLog(liquibaseProperties.getChangeLog());
//    liquibase.setContexts(liquibaseProperties.getContexts());
//    liquibase.setDataSource(getDataSource(liquibaseProperties));
//    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
//    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
//    liquibase.setShouldRun(true);
//    liquibase.setLabels(liquibaseProperties.getLabels());
//    liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
//    return liquibase;
//  }
//
//  private DataSource getDataSource(LiquibaseProperties liquibaseProperties) {
//    if (liquibaseProperties.getUrl() == null) {
//      return this.dataSource;
//    }
//    return DataSourceBuilder.create().url(liquibaseProperties.getUrl())
//        .username(liquibaseProperties.getUser())
//        .password(liquibaseProperties.getPassword()).build();
//  }


}