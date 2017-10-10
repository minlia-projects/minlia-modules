package com.minlia.cloud.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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
      ".**.repository"}, considerNestedRepositories = true, transactionManagerRef = "jpaTransactionManager")
  @EnableJpaAuditing
  @Configuration
  @ConditionalOnClass(CloudDataJpaAutoConfiguration.class)
  public static class EnableMinliaCloudJpaRepository {

    @Autowired
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;



//    @Autowired
//    private MultiTenantConnectionProvider multiTenantConnectionProvider;
//
//    @Autowired
//    private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;
//
//
//    protected Map<String, Object> getJpaProperties() {
//      Map<String, Object> map = new HashMap<>();
//      map.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
//      map.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver);
//      map.put("hibernate.multiTenancy", "DATABASE");
//      return map;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
//      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//      vendorAdapter.setGenerateDdl(true);
//      vendorAdapter.setDatabasePlatform(getDatabaseDialect().getName());
//      vendorAdapter.setShowSql(true);
//
//      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//      factory.setJpaVendorAdapter(vendorAdapter);
//      factory.setPackagesToScan(getEntityPackage());
//      factory.setJpaPropertyMap(getJpaProperties());
//      factory.afterPropertiesSet();
//      return factory;
//    }
//
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//      return entityManagerFactory.createEntityManager();
//    }



      @Primary
    @Bean
    public JpaTransactionManager jpaTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager
          .setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
      return transactionManager;
    }
  }


  //从自动装配改变为手动装配
  @Autowired
  private DataSource dataSource;




  @Bean
  public LiquibaseProperties liquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean
  @DependsOn(value = "entityManagerFactory")
  public SpringLiquibase liquibase() {
    LiquibaseProperties liquibaseProperties = liquibaseProperties();
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog(liquibaseProperties.getChangeLog());
    liquibase.setContexts(liquibaseProperties.getContexts());
    liquibase.setDataSource(getDataSource(liquibaseProperties));
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    liquibase.setShouldRun(true);
    liquibase.setLabels(liquibaseProperties.getLabels());
    liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    return liquibase;
  }

  private DataSource getDataSource(LiquibaseProperties liquibaseProperties) {
    if (liquibaseProperties.getUrl() == null) {
      return this.dataSource;
    }
    return DataSourceBuilder.create().url(liquibaseProperties.getUrl())
        .username(liquibaseProperties.getUser())
        .password(liquibaseProperties.getPassword()).build();
  }


}