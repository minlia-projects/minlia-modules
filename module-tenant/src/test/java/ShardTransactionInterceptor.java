//
//public class ShardTransactionInterceptor implements MethodInterceptor {
//    private static final AppLogger logger = AppLogger.getLogger(ShardTransactionInterceptor.class);
//    private static ThreadLocal dataSourceThreadLocal = new ThreadLocal();
//    private ShardDataSourceManager shardDataSourceManager;
//
//    public ShardDataSourceManager getShardDataSourceManager() {
//        return shardDataSourceManager;
//    }
//
//    public void setShardDataSourceManager(ShardDataSourceManager shardDataSourceManager) {
//        this.shardDataSourceManager = shardDataSourceManager;
//    }
//
//
//    @Override
//    public Object invoke(final MethodInvocation method) throws Throwable {
//        if (method.getMethod().isAnnotationPresent(ShardTransactional.class)) {
//            try {
//                ShardTransactional annotation = method.getMethod().getAnnotation(ShardTransactional.class);
//                User user = getParam(method, User.class);
//                if (user == null) {
//                    throw new IllegalStateException("All transactional methods must have user argument");
//                }
//                TransactionTemplate transactionTemplate = new TransactionTemplate();
//                boolean readOnly = annotation.readOnly();
//                transactionTemplate.setReadOnly(readOnly);
//                ShardInfo shardInfo =  getShardInfo(user);
//                transactionTemplate.setName("ShardTransaction");
//                transactionTemplate.setTransactionManager(shardDataSourceManager.getTransactionManagerByHostId(shardInfo.getHostId(), readOnly));
//                cacheDataSourceInThreadLocal(shardInfo.getHostId(),readOnly);
//                return transactionTemplate.execute(new TransactionCallback() {
//                    @Override
//                    public Object doInTransaction(TransactionStatus transactionStatus) {
//                        try {
//                            return method.proceed();
//                        }catch (Throwable t) {
//                            transactionStatus.setRollbackOnly();
//                            logger.error("Rolling back transaction due to" ,t);
//                            throw new RuntimeException(t);
//                        }
//                    }
//                });
//            } finally {
//                dataSourceThreadLocal.set(null);
//            }
//        } else {
//            return method.proceed();
//        }
//    }
//
//    private ShardInfo getShardInfo(User user) {
//        ...code to lookup shard by user
//        return shardInfo;
//    }
//
//    public static DataSource getDataSource() {
//        return dataSourceThreadLocal.get();
//    }
//
//    private DataSource cacheDataSourceInThreadLocal(int hostId, boolean readOnly) {
//        DataSource datasource = shardDataSourceManager.getDataSourceByHostId(hostId, readOnly);
//        dataSourceThreadLocal.set(datasource);
//        return datasource;
//    }
//
//    private T getParam(MethodInvocation method, Class clazz) {
//        Method reflectMethod = method.getMethod();
//        Class[] parameterTypes = reflectMethod.getParameterTypes();
//        if (parameterTypes != null) {
//            int i=0;
//            boolean found = false;
//            for (Class parameterType : parameterTypes) {
//                if(clazz.isAssignableFrom(parameterType)) {
//                    found = true;
//                    break;
//                }
//                i++;
//            }
//            if (found) {
//                T param = (T) method.getArguments()[i];
//                return param;
//            }
//        }
//        return null;
//    }
//}
