//package com.minlia.module.tenant.hibernate.config;
//
//import com.minlia.module.tenant.hibernate.provider.HibernateTenantConnectionProvider;
//import com.minlia.module.tenant.hibernate.resolve.BatisTenantIdentifierResolver;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//public class HibernateJpaConfig {
//
//  @Autowired
//  private TenantDataSourceConfig tenantDataSourceConfig;
//
//  @Bean
//  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//    Properties jpaProperties = new Properties();
//    jpaProperties.put("hibernate.multiTenancy", "DATABASE");
//    jpaProperties
//        .put("hibernate.tenant_identifier_resolver", BatisTenantIdentifierResolver.class.getName());
//
//    Map<String, DataSource> dataSourceMap = new HashMap<>();
//    dataSourceMap.put("at", tenantDataSourceConfig.dataSourceAt());
//    dataSourceMap.put("hu", tenantDataSourceConfig.dataSourceHu());
//    jpaProperties.put("hibernate.multi_tenant_connection_provider",
//        new HibernateTenantConnectionProvider(dataSourceMap));
//
//    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//    emf.setJpaVendorAdapter(jpaVendorAdapter());
//    emf.setJpaProperties(jpaProperties);
//    emf.setPackagesToScan("mt.model");
//    emf.setDataSource(tenantDataSourceConfig.dataSourceAt());
//
//    return emf;
//  }
//
//  @Bean
//  public JpaVendorAdapter jpaVendorAdapter() {
//    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//    adapter.setShowSql(true);
//    adapter.setDatabase(Database.H2);
//    adapter.setGenerateDdl(false);
//    return adapter;
//  }
//
//  @Bean
//  public PlatformTransactionManager transactionManager() {
//    return new JpaTransactionManager();
//  }
//
//}
