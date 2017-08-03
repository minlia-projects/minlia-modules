package com.minlia.cloud.query.specification.batis.plugins.plugins;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.query.body.ApiSearchRequestBody;
import com.minlia.cloud.query.specification.batis.BatisSpecifications;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.plugins.dialect.Dialect;
import com.minlia.cloud.util.QueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分页插件，使用 Pageable 和 Page 对象作为输入和输出。
 * 只要方法参数中包含 Pageable 参数，则自动分页。
 * <p>
 * 方言类和部分逻辑来自： https://github.com/miemiedev/mybatis-paginator
 *
 * @author zany@buzheng.org
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args =
        {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
public class MybatisPageableInterceptor implements Interceptor {


    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;


    private Dialect dialect;

    @Override
    public Object intercept(Invocation inv) throws Throwable {

        final Object[] queryArgs = inv.getArgs();

        // 查找方法参数中的 分页请求对象
        Pageable pageRequest = this.findPageableObject(queryArgs[PARAMETER_INDEX]);
        Class<?> entityClass= this.findEntityClass(queryArgs[MAPPED_STATEMENT_INDEX]);
        ApiSearchRequestBody searchRequestBody = this.findSearchObject(queryArgs[PARAMETER_INDEX]);

        String searchStatement="";



        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];

        final BoundSql boundSql = ms.getBoundSql(parameter);

        // 删除尾部的 ';'
        String sql = boundSql.getSql().trim().replaceAll(";$", "");




        if (null != searchRequestBody) {
            log.debug("With ApiSearchRequestBody {}",searchRequestBody);

            BatisSpecifications spec=ContextHolder.getContext().getBean(BatisSpecifications.class);
            SpecificationDetail specificationDetail= spec.buildSpecification(searchRequestBody);
            log.debug("With SpecificationDetail {}",specificationDetail);

            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(entityClass);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
                        specificationDetail.getOrQueryConditions(), Lists.newArrayList(BatisSpecifications.MYBITS_SEARCH_PARAMS_MAP),
                        paramsMap, true);

            log.debug("With sqlConditionDsf {}",sqlConditionDsf);

//            sql=SQLHelper.removeWhere(sql);

            String condition="__ApiSearchRequestBody__";

            sql = sql.replaceAll(condition,  " 1=1 "+sqlConditionDsf);


//            try {
//                Map<String, Object> paramsMap = Maps.newHashMap();
//
//                Class<?> idType = resolveResultJavaType(clazz,
//                        ReflectUtil.getIdFieldName(clazz), null);
//
//                specificationDetail.setPersistentClass(persistentClass);
//                String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
//                        specificationDetail.getOrQueryConditions(),
//                        Lists.newArrayList(BatisSpecifications.MYBITS_SEARCH_PARAMS_MAP),
//                        paramsMap, true);
//                paramsMap.put(BatisSpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//                paramsMap.put(BatisSpecifications.MYBITS_SEARCH_CONDITION, new Object());
//
////                return PublicUtil.isNotEmpty(specificationDetail.getOrders()) ?
////                        repository.findAll(false, new Sort(toOrders(specificationDetail.getOrders())), paramsMap) :
////                        repository.findAll(false, paramsMap);
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                Assert.buildException(e.getMessage());
//            }

        }

        //处理查询条件

        //处理排序

        //处理分页

        // 如果需要分页
        if (pageRequest != null) {



            // 1. 搞定总记录数（如果需要的话）			
            int total = 0;
//			if (pageRequest.getCountable()) {
            total = this.queryTotal(sql, ms, boundSql);
//			}

            // 2. 搞定limit 查询
            // 2.1 获取分页SQL，并完成参数准备
            //this.pageSQL = this.getLimitString(this.pageSQL, "__offset", this.pageBounds.getOffset(), "__limit", this.pageBounds.getLimit());
            String limitSql = dialect.getLimitString(sql, pageRequest.getOffset(), pageRequest.getPageSize());

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, limitSql);

            // 2.2 继续执行剩余步骤，获取查询结果
            Object ret = inv.proceed();

            // 3. 组成分页对象
            @SuppressWarnings({"unchecked"})
            Page<Object> pi = new PageImpl<Object>((List<Object>) ret, pageRequest, total);

            // 4. MyBatis 需要返回一个List对象，这里只是满足MyBatis而作的临时包装
            List<Page<?>> tmp = new ArrayList<Page<?>>(1);
            tmp.add(pi);
            return tmp;
        }

        return inv.proceed();

    }


    private Class<?> resolveResultJavaType(Class<?> resultType,
                                           String property, Class<?> javaType) {
        if (javaType == null && property != null) {
            try {
                MetaClass metaResultType = MetaClass.forClass(resultType,null);
                javaType = metaResultType.getSetterType(property);
            } catch (Exception e) {
                // ignore, following null check statement will deal with the
                // situation
            }
        }
        if (javaType == null) {
            javaType = Object.class;
        }
        return javaType;
    }


    /**
     * 在方法参数中查找 分页请求对象
     *
     * @param params Mapper接口方法中的参数对象
     * @return
     */

    private Pageable findPageableObject(Object params) {

        if (params == null) {
            return null;
        }

        // 单个参数 表现为参数对象
        if (Pageable.class.isAssignableFrom(params.getClass())) {
            return (Pageable) params;
        }

        // 多个参数 表现为 ParamMap
        else if (params instanceof ParamMap) {
            @SuppressWarnings("unchecked")
            ParamMap<Object> paramMap = (ParamMap<Object>) params;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();

                if (paramValue != null && Pageable.class.isAssignableFrom(paramValue.getClass())) {
                    return (Pageable) paramValue;
                }
            }
        }

        return null;
    }
    private Class<?> findEntityClass(Object params) {

        if (params == null) {
            return null;
        }

        // 单个参数 表现为参数对象
        if (MappedStatement.class.isAssignableFrom(params.getClass())) {
            return ((MappedStatement) params).getResultMaps().get(0).getType();
        }
        return null;
    }
    /**
     * 在方法参数中查找 分页请求对象
     *
     * @param params Mapper接口方法中的参数对象
     * @return
     */
    private ApiSearchRequestBody findSearchObject(Object params) {

        if (params == null) {
            return null;
        }

        // 单个参数 表现为参数对象
        if (ApiSearchRequestBody.class.isAssignableFrom(params.getClass())) {
            return (ApiSearchRequestBody) params;
        }

        // 多个参数 表现为 ParamMap
        else if (params instanceof ParamMap) {
            @SuppressWarnings("unchecked")
            ParamMap<Object> paramMap = (ParamMap<Object>) params;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();

                if (paramValue != null && ApiSearchRequestBody.class.isAssignableFrom(paramValue.getClass())) {
                    return (ApiSearchRequestBody) paramValue;
                }
            }
        }

        return null;
    }

    @Override
    public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
        if (Executor.class.isAssignableFrom(target.getClass())) {
            return Plugin.wrap(target, this);
        }

        return target;
    }

    @Override
    public void setProperties(Properties p) {
        String dialectClass = p.getProperty("dialectClass");

        try {
            setDialect((Dialect) Class.forName(dialectClass).newInstance());
        } catch (Exception e) {
            throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
        }

    }

    /**
     * 查询总记录数
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @return
     * @throws SQLException
     */
    private int queryTotal(String sql, MappedStatement mappedStatement,
                           BoundSql boundSql) throws SQLException {

        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {

            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();

            String countSql = this.dialect.getCountString(sql);

            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());

            setParameters(countStmt, mappedStatement, countBoundSql, boundSql.getParameterObject());

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            return totalCount;
        } catch (SQLException e) {
            log.error("查询总记录数出错", e);
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("exception happens when doing: ResultSet.close()", e);
                }
            }

            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    log.error("exception happens when doing: PreparedStatement.close()", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("exception happens when doing: Connection.close()", e);
                }
            }
        }

    }

    /**
     * 对SQL参数(?)设值
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    private MappedStatement copyFromNewSql(MappedStatement ms,
                                           BoundSql boundSql, String sql) {
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
                                      String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    //see: MapperBuilderAssistant
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        //setStatementTimeout()
        builder.timeout(ms.getTimeout());

        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }
}
