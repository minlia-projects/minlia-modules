//package com.minlia.module.email;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.minlia.module.data.entity.AbstractEntity;
//import org.junit.Test;
//
///**
// * 运行此方法生成mybatis代码
// * 生成代码自动放入对应目录
// *
// * @author garen
// * @create 2020/8/10
// */
//public class MyBatisGeneratorRun {
//    /**
//     * 数据库类型
//     */
//    private final DbType dbType = DbType.MYSQL;
//
//    /**
//     * 数据库连结信息
//     */
//    private final String dbUrl = "jdbc:mysql://127.0.0.1:3306/hsjs?useUnicode=true&characterEncoding=UTF8&serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&autoReconnectForPools=true&allowMultiQueries=true";
//    private final String driver = "com.mysql.cj.jdbc.Driver";
//    private final String userName = "root";
//    private final String password = "Root.2020";
//
//    /**
//     * 项目名
//     */
//    private final String projectName = "module-email";
//
//    /**
//     * 模块名 如果有模块名，则需在模块名前加. 例：.log
//     */
//    private final String moduleName = "";
//
//    /**
//     * 指定包名
//     */
//    private final String packageName = "com.minlia.module.email";
//
//    /**
//     * controller基础类
//     */
//    private final String superControllerClass = packageName + ".BaseController";
//
//    /**
//     * entity基础类
//     */
//    private final Class superEntityClass = AbstractEntity.class;
//
//    /**
//     * 作者名
//     */
//    private final String author = "garen";
//
//    /**
//     * 指定生成的表名
//     */
//    private final String[] tableNames = new String[]{"sys_email_from"};
//
//    /**
//     * 表前缀
//     */
//    private final String TABLE_PREFIX = "";
//
//    @Test
//    public void generateCode() {
//        //serviceNameStartWithI：user -> UserService, 设置成true: user -> IUserService
//        generateByTables(false, packageName, tableNames);
//    }
//
//    /**
//     * 根据表自动生成
//     *
//     * @param serviceNameStartWithI 默认为false
//     * @param packageName           包名
//     * @param tableNames            表名
//     * @author Terry
//     */
//    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
//        //配置数据源
//        DataSourceConfig dataSourceConfig = getDataSourceConfig();
//        //策略配置
//        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
//        //包名配置
//        PackageConfig packageConfig = getPackageConfig(packageName);
//        //全局变量配置
//        GlobalConfig globalConfig = getGlobalConfig(serviceNameStartWithI);
//        //自动生成
//        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig);
//    }
//
//    /**
//     * 配置数据源
//     *
//     * @return 数据源配置 DataSourceConfig
//     * @author garen
//     */
//    private DataSourceConfig getDataSourceConfig() {
//        return new DataSourceConfig()
//                .setDbType(dbType)
//                .setDriverName(driver)
//                .setUrl(dbUrl)
//                .setUsername(userName)
//                .setPassword(password);
//    }
//
//    /**
//     * 策略配置
//     *
//     * @param tableNames 表名
//     * @return StrategyConfig
//     * @author garen
//     */
//    private StrategyConfig getStrategyConfig(String... tableNames) {
//        return new StrategyConfig()
//                // 全局大写命名 ORACLE 注意
//                .setCapitalMode(true)
//                //是否跳过视图
//                .setSkipView(true)
//                //从数据库表到文件的命名策略
//                .setNaming(NamingStrategy.underline_to_camel)
//                //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
//                .setColumnNaming(NamingStrategy.underline_to_camel)
//                //表前缀
//                .setTablePrefix(TABLE_PREFIX)
//                //字段前缀
////                .setFieldPrefix()
//
//                //自定义继承的Entity类全称
//                .setSuperEntityClass(superEntityClass)
//                //自定义基础的Entity类，公共字段
////                .setSuperEntityColumns("id")
//                //自定义继承的Mapper类全称，带包名
////                .setSuperMapperClass()
//                //自定义继承的Service类全称，带包名
////                .setSuperServiceClass("com.wool.njs.service.*")
//                //自定义继承的ServiceImpl类全称，带包名
////                .setSuperServiceImplClass("com.wool.njs.service.impl.*")
//                //自定义继承的Controller类全称，带包名
////                .setSuperControllerClass(superControllerClass)
//
//                //需要包含的表名，当enableSqlFilter为false时，允许正则表达式（与exclude二选一配置）
//                .setInclude(tableNames)
//                //需要排除的表名，当enableSqlFilter为false时，允许正则表达式
////                .setExclude()
//
//                //实体】是否为lombok模型（默认 false）
//                .setEntityLombokModel(true)
//                //Boolean类型字段是否移除is前缀（默认 false）
//                .setEntityBooleanColumnRemoveIsPrefix(false)
//                //生成 @RestController 控制器
//                .setRestControllerStyle(true)
//                //驼峰转连字符
//                .setControllerMappingHyphenStyle(false)
//                //是否生成实体时，生成字段注解
//                .setEntityTableFieldAnnotationEnable(true)
//                //乐观锁属性名称
//                .setVersionFieldName("version")
//                //逻辑删除属性名称
//                .setLogicDeleteFieldName("del_flag");
//        //表填充字段
////                .setTableFillList();
//    }
//
//    /**
//     * 集成
//     *
//     * @param dataSourceConfig 配置数据源
//     * @param strategyConfig   策略配置
//     * @param config           全局变量配置
//     * @param packageConfig    包名配置
//     * @author Terry
//     */
//    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig) {
//        new AutoGenerator()
//                .setGlobalConfig(config)
//                .setDataSource(dataSourceConfig)
//                .setStrategy(strategyConfig)
//                .setPackageInfo(packageConfig)
////                .setTemplateEngine(new FreemarkerTemplateEngine())
//                .execute();
//    }
//
//    /**
//     * 设置包名
//     *
//     * @param packageName 父路径包名
//     * @param packageName 模块名
//     * @return PackageConfig 包名配置
//     * @author Terry
//     */
//    private PackageConfig getPackageConfig(String packageName) {
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.println(path);
//        System.out.println(System.getProperty("user.dir"));
//        return new PackageConfig()
//                .setParent(packageName)
//                .setModuleName(moduleName)
//                .setEntity("entity")
//                .setService("service")
//                .setServiceImpl("service.impl")
//                .setMapper("mapper")
////                .setXml(System.getProperty("user.dir") + "/src/main/resource/mybatis/" + packageName)
//                .setController("controller");
//    }
//
//    /**
//     * 全局配置
//     *
//     * @param serviceNameStartWithI false
//     * @return GlobalConfig
//     * @author Terry
//     */
//    private GlobalConfig getGlobalConfig(boolean serviceNameStartWithI) {
//        return new GlobalConfig()
//                //生成文件的输出目录
//                .setOutputDir(getOutputDir(projectName))
//                //是否覆盖已有文件
//                .setFileOverride(true)
//                //是否打开输出目录
//                .setOpen(false)
//                //是否在xml中添加二级缓存配置
//                .setEnableCache(false)
//                //开发人员
//                .setAuthor(author)
//                .setBaseColumnList(true)
//                .setBaseResultMap(true)
//                .setActiveRecord(false)
//
//                .setSwagger2(true)
//
//                .setEntityName("%sEntity")
//                .setMapperName("%sMapper")
//                .setXmlName("%sMapper")
//                .setServiceName("%sService")
//                .setServiceImplName("%sServiceImpl")
//                .setControllerName("%sController")
////                .setIdType(IdType.AUTO)
//                ;
//    }
//
//    /**
//     * 返回项目路径
//     *
//     * @param projectName 项目名
//     * @return 项目路径
//     * @author Terry
//     */
//    private String getOutputDir(String projectName) {
////        String path = this.getClass().getClassLoader().getResource("").getPath();
////        int index = path.indexOf(projectName);
////        String outputDir = path.substring(0, index + projectName.length()) + "/src/main/java";
//        String outputDir = System.getProperty("user.dir") + "/src/main/java";
//        return outputDir;
//    }
//
//    /**
//     * 根据表自动生成
//     *
//     * @param packageName 包名
//     * @param tableNames  表名
//     * @author Terry
//     */
//    @SuppressWarnings("unused")
//    private void generateByTables(String packageName, String... tableNames) {
//        generateByTables(true, packageName, tableNames);
//    }
//
//}