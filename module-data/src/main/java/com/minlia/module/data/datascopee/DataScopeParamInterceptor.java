///*
// *    Copyright (c) 2018-2025, lengleng All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice,
// * this list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright
// * notice, this list of conditions and the following disclaimer in the
// * documentation and/or other materials provided with the distribution.
// * Neither the name of the pig4cloud.com developer nor the names of its
// * contributors may be used to endorse or promote products derived from
// * this software without specific prior written permission.
// * Author: lengleng (wangiegie@gmail.com)
// */
//
//package com.minlia.module.data.datascopee;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.util.ReflectUtil;
//import cn.hutool.db.Db;
//import cn.hutool.db.Entity;
//import com.minlia.cloud.utils.ApiAssert;
//import com.minlia.modules.security.context.MinliaSecurityContextHolder;
//import com.minlia.modules.security.model.UserContext;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
///**
// * mybatis 数据权限拦截器
// *
// * @author garen
// * @date 2019/8/13
// */
//@Slf4j
//@Component
//@AllArgsConstructor
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
//public class DataScopeParamInterceptor implements Interceptor {
//
//    private final DataSource dataSource;
//
//    private final static String SCOPE_SPLIT_SQL = "select * from (%s) temp_data_scope where temp_data_scope.%s in (%s)";
//
//    private final static String ROLE_SPLIT_SQL = "select * FROM mdl_role where code = '%s'";
//
//    @Override
//    @SneakyThrows
//    public Object intercept(Invocation invocation) {
//        if (log.isInfoEnabled()) {
//            log.info("进入 DataScopeParamInterceptor 拦截器...");
//        }
//
//        if (invocation.getTarget() instanceof StatementHandler) {
//            StatementHandler handler = (StatementHandler) invocation.getTarget();
//
//            //由于mappedStatement为protected的，所以要通过反射获取
//            MetaObject statementHandler = SystemMetaObject.forObject(handler);
//
//            //mappedStatement中有我们需要的方法id
//            MappedStatement mappedStatement = (MappedStatement) statementHandler.getValue("delegate.mappedStatement");
//
//            //先判断是不是SELECT操作
//            if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
//                return invocation.proceed();
//            }
//
//            BoundSql boundSql = handler.getBoundSql();
//            Object parameterObject = boundSql.getParameterObject();
//
//            //查找参数中包含DataScope类型的参数
//            DataScopeParam dataScope = findDataScopeObject(parameterObject);
//            if (dataScope == null) {
//                return invocation.proceed();
//            }
//
//            String sql = scopeSql(boundSql.getSql(), dataScope, dataSource);
//            ReflectUtil.setFieldValue(boundSql, "sql", sql);
//        }
//        return invocation.proceed();
//    }
//
//    @SneakyThrows
//    private static String scopeSql(String sql, DataScopeParam dataScope, DataSource dataSource) {
//        List<Long> scopeIds = dataScope.getValues();
//
//        // 优先获取赋值数据
//        if (CollUtil.isEmpty(scopeIds)) {
//            UserContext user = MinliaSecurityContextHolder.getUserContext();
//            ApiAssert.notNull(user, "Anonymous users are prohibited from accessing");
//            ApiAssert.notNull(user.getOrgId(), "Anonymous users are prohibited from accessing");
//
//            //获取数据范围类型
//            Entity query = Db.use(dataSource)
//                    .query(String.format(ROLE_SPLIT_SQL, user.getCurrrole()))
//                    .stream().min(Comparator.comparingInt(o -> o.getInt("ds_type"))).get();
//            Integer dsType = query.getInt("ds_type");
//            ApiAssert.notNull(dsType, "Data scope not configured");
//
//            // 查询全部
//            if (DataScopeTypeEnum.ALL.getType() == dsType) {
//                return sql;
//            } else if (DataScopeTypeEnum.CUSTOMIZE.getType() == dsType) {
//                String dsScope = query.getStr("ds_scope");
//                scopeIds.addAll(Arrays.stream(dsScope.split(","))
//                        .map(Long::parseLong).collect(Collectors.toList()));
//            } else if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType() == dsType) {
//                List<Long> deptIdList = Db.use(dataSource)
//                        .findBy("mdl_organization_relation", "ancestor", user.getOrgId())
//                        .stream().map(entity -> entity.getLong("descendant"))
//                        .collect(Collectors.toList());
//                scopeIds.addAll(deptIdList);
//            } else if (DataScopeTypeEnum.OWN_LEVEL.getType() == dsType) {
//                scopeIds.add(user.getOrgId());
//            }
//        }
//
//        String join = CollectionUtil.join(scopeIds, ",");
//        sql = String.format(SCOPE_SPLIT_SQL, sql, dataScope.getName(), join);
//        return sql;
//    }
//
//    /**
//     * 生成拦截对象的代理
//     *
//     * @param target 目标对象
//     * @return 代理对象
//     */
//    @Override
//    public Object plugin(Object target) {
//        if (target instanceof StatementHandler) {
//            return Plugin.wrap(target, this);
//        }
//        return target;
//    }
//
//    /**
//     * mybatis配置的属性
//     *
//     * @param properties mybatis配置的属性
//     */
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//
//    /**
//     * 查找参数是否包括DataScope对象
//     *
//     * @param parameterObj 参数列表
//     * @return DataScope
//     */
//    private DataScopeParam findDataScopeObject(Object parameterObj) {
//        if (parameterObj instanceof DataScopeParam) {
//            return (DataScopeParam) parameterObj;
//        } else if (parameterObj instanceof Map) {
//            for (Object val : ((Map<?, ?>) parameterObj).values()) {
//                if (val instanceof DataScopeParam) {
//                    return (DataScopeParam) val;
//                }
//            }
//        }
//        return null;
//    }
//
//}
