package com.minlia.module.tenant.batis.interceptor;

import com.minlia.module.tenant.batis.provider.SchemaBasedTenantConnectionProvider;
import com.minlia.module.tenant.batis.provider.TenantConnectionProvider;
import com.minlia.module.tenant.batis.resolver.ThreadLocalTenantIdentifierResolver;
import com.minlia.module.tenant.batis.resolver.TenantIdentifierResolver;
import java.sql.Connection;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,
        Integer.class})})
@Slf4j
public class MultiTenantInterceptor implements Interceptor {

//  private TableIncludingProvider tableIncludingProvider;


  private TenantIdentifierResolver tenantIdentifierResolver;

  private TenantConnectionProvider connectionProvider;
  public MultiTenantInterceptor() {

  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    final Object[] queryArgs = invocation.getArgs();
    Connection connection = (Connection) queryArgs[0];
    queryArgs[0] = this.applyTenantConnection(connection);
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof StatementHandler) {
      return Plugin.wrap(target, this);
    } else {
      return target;
    }
  }

  @Override
  public void setProperties(Properties properties) {

  }

  /**
   * 应用Connection
   */
  public Connection applyTenantConnection(Connection connection) throws Exception {
    if (this.connectionProvider == null) {
      this.connectionProvider = new SchemaBasedTenantConnectionProvider();
      this.connectionProvider.configure(connection.getMetaData());
    }
    if (this.tenantIdentifierResolver == null) {
      this.tenantIdentifierResolver = new ThreadLocalTenantIdentifierResolver();
    }
    String tenantIdentifier = this.tenantIdentifierResolver.resolve();
    return this.connectionProvider.getConnection(tenantIdentifier, connection);
  }

  public TenantConnectionProvider getConnectionProvider() {
    return connectionProvider;
  }

  public void setConnectionProvider(TenantConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  public TenantIdentifierResolver getTenantIdentifierResolver() {
    return tenantIdentifierResolver;
  }

  public void setTenantIdentifierResolver(
      TenantIdentifierResolver tenantIdentifierResolver) {
    this.tenantIdentifierResolver = tenantIdentifierResolver;
  }
}
