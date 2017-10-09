package com.minlia.module.tenant.hibernate.provider;

import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

/**
 * JPA多租户连接提供方
 */
public class TenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

  private static final long serialVersionUID = 0L;

  private Map<String, DataSource> dataSourceMap;

  public TenantConnectionProvider(Map<String, DataSource> dataSourceMap) {
    this.dataSourceMap = Collections.unmodifiableMap(dataSourceMap);
  }

  @Override
  protected ConnectionProvider getAnyConnectionProvider() {
    return getConnectionProvider(ThreadLocalBatisTenantIdentifierResolver.getCurrentTenantIdentifier());
  }

  @Override
  protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
    return getConnectionProvider(tenantIdentifier);
  }

  private ConnectionProvider getConnectionProvider(String tenantIdentifier) {
    DataSource dataSource = dataSourceMap.get(tenantIdentifier);

    DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
    connectionProvider.setDataSource(dataSource);

    try {
      connectionProvider.getConnection().setCatalog("");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connectionProvider.configure(Collections.emptyMap());
    return connectionProvider;
  }

}
