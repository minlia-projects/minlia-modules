package com.minlia.module.tenant.hibernate.provider;

import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;

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

  @Autowired()
  DataSource dataSource;

  private ConnectionProvider getConnectionProvider(String appid) {

    try {
      dataSource.getConnection().setCatalog(appid);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
    connectionProvider.setDataSource(dataSource);
    connectionProvider.configure(Collections.emptyMap());
    return connectionProvider;
  }

}
