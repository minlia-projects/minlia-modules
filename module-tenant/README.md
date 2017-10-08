
Module-Tenant


tenant模块基于mybatis和spring-web的 Filter
所以需要在一个有数据库和web的项目中使用


TenantFilter
TenantHeaderFilter
TenantInterceptor

AppidInterceptor
AppidHeaderFilter
AppidContextHolder


    @Bean
    public FilterRegistrationBean tenantFilter()
    {
        FilterRegistrationBean filter = new FilterRegistrationBean(new TenantFilter());
        //这里进行路径定义
        filter.addUrlPatterns("/*");
        return filter;
    }
    

通过WEB层与数据层2个层面进行需要隔离和不隔离的控制

注册一个表包含的BEAN
非此包含列表的都不做处理
