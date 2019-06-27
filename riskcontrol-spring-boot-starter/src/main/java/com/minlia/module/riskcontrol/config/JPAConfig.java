package com.minlia.module.riskcontrol.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.minlia.**.entity"})
@EnableJpaRepositories(
        // basePackages 支持多包扫描，用文本数组的形式就可以
        // 比如这样 {"com.simply.zuozuo.repo","com.simply.zuozuo.mapper"}
        basePackages = {
                "com.minlia.**.repository"
        },
        value = {},
        // 指定里面的存储库类
        basePackageClasses = {},
        // 包含的过滤器
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)
        },
        // 不包含的过滤器
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        },
        // 通过什么后缀来命名实现类，比如接口A的实现，名字叫AImpl
        repositoryImplementationPostfix = "Impl",
        // named SQL存放的位置，默认为META-INF/jpa-named-queries.properties
        namedQueriesLocation = "",
        // 枚举中有三个值，
        // CREATE_IF_NOT_FOUND，先搜索用户声明的，不存在则自动构建
        // USE_DECLARED_QUERY，用户声明查询
        // CREATE，按照接口名称自动构建查询
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND,
        // 指定Repository的工厂类
        repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class,
        // 指定Repository的Base类
        repositoryBaseClass = DefaultRepositoryBaseClass.class,
        // 实体管理工厂引用名称，对应到@Bean注解对应的方法
        entityManagerFactoryRef = "entityManagerFactory",
        // 事务管理工厂引用名称，对应到@Bean注解对应的方法
        transactionManagerRef = "transactionManager",
        // 是否考虑嵌套存储库
        considerNestedRepositories = false,
        // 开启默认事务
        enableDefaultTransactions = true
)
public class JPAConfig {


}