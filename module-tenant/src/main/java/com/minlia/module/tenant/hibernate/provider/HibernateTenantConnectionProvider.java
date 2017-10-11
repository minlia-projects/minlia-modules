package com.minlia.module.tenant.hibernate.provider;

import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mybatis.repository.util.StringUtils;

/**
 * JPA多租户连接提供方
 */
public class HibernateTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

  private static final long serialVersionUID = 0L;

  public HibernateTenantConnectionProvider() {
  }

  @Override
  public Connection getConnection(String appid) throws SQLException {
    return selectConnectionProvider(appid).getConnection();
  }

  @Override
  protected ConnectionProvider getAnyConnectionProvider() {
    return getConnectionProvider(
        ThreadLocalBatisTenantIdentifierResolver.getCurrentTenantIdentifier());
  }

  @Override
  protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
    return getConnectionProvider(tenantIdentifier);
  }


  private ConnectionProvider getConnectionProvider(String appid) {
    DatasourceConnectionProviderImpl connectionProvider = null;
    Boolean bypass=ThreadLocalBatisTenantIdentifierResolver.getBypass();
    if(null == bypass || !bypass) {
      //系统初始化时dataSource Bean还没注册进容器, 所以当不为空的时候才执行以下代码
      ApplicationContext applicationContext = ContextHolder.getContext();
      DataSource dataSource = null;

      if (null != applicationContext) {
        dataSource = applicationContext.getBean(DataSource.class);
      }
      if (null != dataSource) {
        try {
          if (!StringUtils.isEmpty(appid)) {
            dataSource.getConnection().setCatalog(appid);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return datasourceConnectionProvider(dataSource);
      } else {


        return datasourceConnectionProvider(masterDataSource());
      }
    }
    return datasourceConnectionProvider(masterDataSource());
  }

  private static DatasourceConnectionProviderImpl datasourceConnectionProvider;
  private synchronized ConnectionProvider  datasourceConnectionProvider(DataSource dataSource){
    if(null==datasourceConnectionProvider) {
      DatasourceConnectionProviderImpl connectionProvider=null;
      connectionProvider = new DatasourceConnectionProviderImpl();
      connectionProvider.setDataSource(dataSource);
      connectionProvider.configure(Collections.emptyMap());
      datasourceConnectionProvider=connectionProvider;
    }
    return datasourceConnectionProvider;
  }
  private DataSource masterDataSource;

  public synchronized DataSource masterDataSource(){

    if(null==masterDataSource) {
      HikariConfig hikariConfig = new HikariConfig();
      hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
      hikariConfig.setJdbcUrl(
          "jdbc:mysql://localhost:3306/minlia_cloud_sample_rebecca_dev_v1?createDatabaseIfNotExist=true&useUnicode=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&verifyServerCertificate=false&useSSL=false&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&allowMultiQueries=true&readOnly=false");
      hikariConfig.setUsername("root");
      hikariConfig.setPassword("");

      hikariConfig.setMaximumPoolSize(5);
      hikariConfig.setConnectionTestQuery("SELECT 1");
      hikariConfig.setPoolName("springHikariCP");

      hikariConfig.addDataSourceProperty("masterDataSource.cachePrepStmts", "true");
      hikariConfig.addDataSourceProperty("masterDataSource.prepStmtCacheSize", "250");
      hikariConfig.addDataSourceProperty("masterDataSource.prepStmtCacheSqlLimit", "2048");
      hikariConfig.addDataSourceProperty("masterDataSource.useServerPrepStmts", "true");

      HikariDataSource dataSource = new HikariDataSource(hikariConfig);
      masterDataSource = dataSource;
    }
    return masterDataSource;
  }


}
