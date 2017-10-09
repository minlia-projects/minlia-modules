package com.minlia.module.tenant.batis.interceptor;

import com.minlia.module.tenant.batis.provider.SchemaBasedBatisTenantConnectionProvider;
import com.minlia.module.tenant.batis.provider.BatisTenantConnectionProvider;
import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import com.minlia.module.tenant.resolver.BatisTenantIdentifierResolver;
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


  private BatisTenantIdentifierResolver batisTenantIdentifierResolver;

  private BatisTenantConnectionProvider connectionProvider;
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
      this.connectionProvider = new SchemaBasedBatisTenantConnectionProvider();
      this.connectionProvider.configure(connection.getMetaData());
    }
    if (this.batisTenantIdentifierResolver == null) {
      this.batisTenantIdentifierResolver = new ThreadLocalBatisTenantIdentifierResolver();
    }
    String tenantIdentifier = this.batisTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    return this.connectionProvider.getConnection(tenantIdentifier, connection);
  }

  public BatisTenantConnectionProvider getConnectionProvider() {
    return connectionProvider;
  }

  public void setConnectionProvider(BatisTenantConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  public BatisTenantIdentifierResolver getBatisTenantIdentifierResolver() {
    return batisTenantIdentifierResolver;
  }

  public void setBatisTenantIdentifierResolver(
      BatisTenantIdentifierResolver batisTenantIdentifierResolver) {
    this.batisTenantIdentifierResolver = batisTenantIdentifierResolver;
  }
}
