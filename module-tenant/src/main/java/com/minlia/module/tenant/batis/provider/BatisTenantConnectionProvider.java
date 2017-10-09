package com.minlia.module.tenant.batis.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * 多租户Connection提供器
 */
public interface BatisTenantConnectionProvider {

  Connection getConnection(String tenantIdentifier, Connection connection) throws SQLException;

  void configure(DatabaseMetaData dbMetaData) throws SQLException;
}
