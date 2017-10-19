//import java.util.HashMap;
//import java.util.Map;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ShardDataSourceManager {
//
//    private static boolean autoCommit = false;
//
//    private Map dataSourceMap = new HashMap();
//
//    private Map transactionManagerMap = new HashMap();
//
//    private ShardManager shardManager;
//
//    private String driverClassName = "org.gjt.mm.mysql.Driver";
//
//    private int maxActive = 20;
//
//    private int maxIdle = 5;
//
//    private int maxWait = 180000;
//
//    private int minEvictableIdleTimeMillis = 300000;
//
//    private boolean testWhileIdle = true;
//
//    private String validationQuery = "select 1 from dual";
//
//    private String userName;
//
//    private String userPassword;
//
//    public String getDriverClassName() {
//        return driverClassName;
//    }
//
//    public void setDriverClassName(String driverClassName) {
//        this.driverClassName = driverClassName;
//    }
//
//    public int getMaxActive() {
//        return maxActive;
//    }
//
//    public void setMaxActive(int maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    public int getMaxIdle() {
//        return maxIdle;
//    }
//
//    public void setMaxIdle(int maxIdle) {
//        this.maxIdle = maxIdle;
//    }
//
//    public int getMaxWait() {
//        return maxWait;
//    }
//
//    public void setMaxWait(int maxWait) {
//        this.maxWait = maxWait;
//    }
//
//    public int getMinEvictableIdleTimeMillis() {
//        return minEvictableIdleTimeMillis;
//    }
//
//    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
//        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
//    }
//
//    public boolean isTestWhileIdle() {
//        return testWhileIdle;
//    }
//
//    public void setTestWhileIdle(boolean testWhileIdle) {
//        this.testWhileIdle = testWhileIdle;
//    }
//
//    public String getValidationQuery() {
//        return validationQuery;
//    }
//
//    public void setValidationQuery(String validationQuery) {
//        this.validationQuery = validationQuery;
//    }
//
//    public String getUserPassword() {
//        return userPassword;
//    }
//
//    public void setUserPassword(String userPassword) {
//        this.userPassword = userPassword;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void init() throws Exception {
//        for (DbHost shardInfo : shardManager.getDbHosts()) {
//            String url = "jdbc:mysql://" + shardInfo.getMasterHost();
//            BasicDataSource dataSource = createDataSource(url, username);
//            dataSourceMap.put(shardInfo.getHostId(), dataSource);
//            DataSourceTransactionManager masterTransactionManager = new DataSourceTransactionManager(dataSource);
//            transactionManagerMap.put(shardInfo.getHostId(), masterTransactionManager);
//            logger.info("DataSource Created for hostid= {}, url= {}", shardInfo.getHostId(), dataSource.getUrl());
//        }
//    }
//
//    private BasicDataSource createDataSource(String url, String username) {
//        logger.info("Initing {} ", url);
//        logger.info("Creating Datasource {}", url);
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(userPassword);
//        dataSource.setValidationQuery(validationQuery);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setConnectionProperties("useUnicode=true;characterEncoding=utf8");
//        dataSource.setDefaultAutoCommit(autoCommit);
//        dataSource.setMaxIdle(maxIdle);
//        dataSource.setMaxWait(maxWait);
//        dataSource.setMaxActive(maxActive);
//        return dataSource;
//    }
//
//    private DataSource getDataSourceByHostId(int hostId) {
//        DataSource dataSource = dataSourceMap.get(hostId);
//        if (dataSource == null) {
//            logger.warn("Could not find a data source for: {}", hostId);
//            throw new IllegalArgumentException("Invalid dbname, no such pool configured: " + hostId);
//        }
//        return dataSource;
//    }
//
//    public DataSource getDataSourceByHostId(int hostId, boolean readOnly) {
//        DataSource dataSource = null;
//        if (dataSource == null) {
//            logger.debug("Using Master datasource for hostid={}", hostId);
//            dataSource = dataSourceMap.get(hostId);
//        }
//        if (dataSource == null) {
//            String msg = "Could not find a data source for hostId=" + hostId;
//            throw new IllegalArgumentException(msg);
//        }
//        return dataSource;
//    }
//
//    public DataSourceTransactionManager getTransactionManagerByHostId(int hostId, boolean readOnly) {
//        DataSourceTransactionManager transactionManager = null;
//        if (transactionManager == null) {
//            logger.debug("Using Master transactionmanager for hostid={}", hostId);
//            transactionManager = transactionManagerMap.get(hostId);
//        }
//        if (transactionManager == null) {
//            String msg = "Could not find a data source for hostId=" + hostId;
//            throw new IllegalArgumentException(msg);
//        }
//        return transactionManager;
//    }
//
//    public void destroy() throws Exception {
//        logger.info("destroying pools");
//        destroyPool(dataSourceMap);
//        transactionManagerMap.clear();
//    }
//
//    private void destroyPool(Map dsMap) throws SQLException {
//        if (dsMap != null) {
//            for (BasicDataSource dataSource : dsMap.values()) {
//                logger.info("Discarding pools: {}", dataSource);
//                dataSource.close();
//            }
//        }
//    }
//}