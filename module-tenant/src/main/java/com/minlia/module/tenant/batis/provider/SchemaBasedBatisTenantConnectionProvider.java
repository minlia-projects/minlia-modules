package com.minlia.module.tenant.batis.provider;


import com.minlia.module.tenant.resolver.ThreadLocalBatisTenantIdentifierResolver;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class SchemaBasedBatisTenantConnectionProvider implements BatisTenantConnectionProvider {
    private static Pattern encodeStringPatterDatabaseName = Pattern.compile("databaseName=(.*?);", Pattern.CASE_INSENSITIVE );
    private static final String SCHEMA_CHANGE_SQL_ORACLE="ALTER SESSION SET CURRENT_SCHEMA = ";
    private static final String SCHEMA_CHANGE_SQL_T="USE ";
    private String schema_change_sql=SCHEMA_CHANGE_SQL_T;
    private boolean isOracle = false;
    private String defaultSchema;
    private Boolean isDefaultSchema;

    @Override
    public Connection getConnection(String tenantIdentifier, Connection connection) throws SQLException {
        if(!StringUtils.isEmpty(tenantIdentifier)) {


            Boolean bypass = ThreadLocalBatisTenantIdentifierResolver.getBypass();
            if(null == bypass || !bypass) {
                applyTenantSchema(tenantIdentifier, connection);
            }
        }
        return connection;
    }

    @Override
    public void configure(DatabaseMetaData dbMetaData) throws SQLException {
        String driverName = dbMetaData.getDriverName();
        if (driverName.toLowerCase().contains("oracle")) {
            this.isOracle = true;
            this.schema_change_sql=SCHEMA_CHANGE_SQL_ORACLE;
            this.defaultSchema=dbMetaData.getUserName();
        }else if (driverName.toLowerCase().contains("mysql")){
            String url=dbMetaData.getURL();
            int start=url.lastIndexOf("/")+1;
            int end=url.indexOf("?");
            this.defaultSchema=url.substring(start,end==-1?url.length():end);
//            log.info("Default Schema {}", this.defaultSchema);
        }else if (driverName.toLowerCase().contains("sql server")){
            String url=dbMetaData.getURL();
            Matcher m = encodeStringPatterDatabaseName.matcher(url);
            if(m.find()){
                this.defaultSchema=m.group(1);
            }else{
                this.defaultSchema=dbMetaData.getUserName();
            }
        }else{
            this.defaultSchema=dbMetaData.getUserName();
        }
    }

    /**
     * 应用租户schema
     * @param tenantIdentifier
     * @param connection
     * @throws SQLException
     */
    private void applyTenantSchema(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            /**
             * ORACLE:ALTER SESSION SET CURRENT_SCHEMA = ${tenantIdentifier}
             * T-SQL:USE ${tenantIdentifier}
             */
            String schema= StringUtils.hasText(tenantIdentifier)?tenantIdentifier:this.defaultSchema;
            if(!org.apache.commons.lang3.StringUtils.isEmpty(schema)) {
                if(!defaultSchema.equalsIgnoreCase(schema)) {
                log.info("Database changed to {}", schema);
                    connection.setCatalog(schema);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Could not alter JDBC connection to specified schema [" + tenantIdentifier
                    + "]", e);
        }
    }
}
