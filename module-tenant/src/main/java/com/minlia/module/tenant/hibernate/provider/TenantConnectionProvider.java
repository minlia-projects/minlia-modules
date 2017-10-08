package com.minlia.module.tenant.hibernate.provider;

import com.minlia.module.tenant.hibernate.TenantThreadLocal;
import java.util.Collections;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class TenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

  private static final long serialVersionUID = -4838344013290465388L;

  private Map<String, DataSource> dataSourceMap;

  public TenantConnectionProvider(Map<String, DataSource> dataSourceMap) {
    this.dataSourceMap = Collections.unmodifiableMap(dataSourceMap);
  }

  @Override
  protected ConnectionProvider getAnyConnectionProvider() {
    return getConnectionProvider(TenantThreadLocal.getTenant());
  }

  @Override
  protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
    return getConnectionProvider(tenantIdentifier);
  }

  private ConnectionProvider getConnectionProvider(String tenantIdentifier) {
    DataSource dataSource = dataSourceMap.get(tenantIdentifier);

    DatasourceConnectionProviderImpl connectionProvider = new DatasourceConnectionProviderImpl();
    connectionProvider.setDataSource(dataSource);
    connectionProvider.configure(Collections.emptyMap());
    return connectionProvider;
  }

}
