package com.minlia.cloud.data.batis.service;

import com.google.common.collect.Maps;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.data.batis.PublicUtil;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.QuerySpecifications;
import com.minlia.cloud.query.specification.batis.QueryUtil;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 8/7/17.
 */
@Slf4j
public class AbstractQueryService<REPOSITORY extends BatisDao<ENTITY, PK>,
        ENTITY extends Persistable<PK>, PK extends Serializable> implements BatisQueryService<REPOSITORY, ENTITY, PK> {

    public Class<ENTITY> persistentClass;

    public AbstractQueryService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            persistentClass = (Class<ENTITY>) parameterizedType[1];
        }
    }

    @Autowired
    REPOSITORY repository;


    /**
     * 动态分页查询
     *
     * @param pageable            分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(persistentClass);
            String reason=String.format("%s无正确的参数化类型,请带参数使用. 如: UserQueryService<UserDao,User,Long>",this.getClass().getName());
            ApiPreconditions.checkNotNull(persistentClass, ApiCode.NOT_NULL,reason);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
                    specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    null,
                    paramsMap, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());


            Boolean pageInstance = PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement);
            pageable = constructPageable(pageable);
            if (pageInstance) {

                return repository.findAll(selectStatement, countStatement, pageable, paramsMap);
//                pageable.setPageInstance(repository.findAll(selectStatement, countStatement, pageable, paramsMap));
            } else {
//                pageable.setPageInstance(repository.findAll(isBasic, pageable, paramsMap));
                return repository.findAll(isBasic, pageable, paramsMap);
            }

//            pageable.setPageInstance(PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement) ?
//                    repository.findAll(selectStatement, countStatement, pageable, paramsMap) :
//                    repository.findAll(isBasic, pageable, paramsMap));
//            return pageable;
        } catch (Exception e) {
            log.error("error: {}", e);
            Assert.buildException(e.getMessage());
        }
        return null;
    }

    private Pageable constructPageable(Pageable pageable) {
        Integer size = 15;
        Integer page = 0;

        Sort.Direction direction = Sort.Direction.DESC;

        String property = "id";

        if ((pageable.getPageSize() < 1000) || pageable.getPageSize() > 0) {
            size = pageable.getPageSize();
        }

        if (pageable.getPageNumber() > -1) {
            page = pageable.getPageNumber();
        }

        if (null != pageable.getSort()) {
            return new PageRequest(page, size, pageable.getSort());
        }

        return new PageRequest(page, size, direction, property);

    }

    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, boolean isBasic) {
        QueryCondition queryCondition = QueryCondition.ne("enabled", false);
//        QueryCondition queryCondition1=QueryCondition.gt(BaseEntity.F_VERSION, 0);
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.buildSpecification("",
                persistentClass,
                queryCondition);
        if (PublicUtil.isNotEmpty(authQueryConditions)) {
            specificationDetail.orAll(authQueryConditions);

        }
        return findBasePage(pageable, specificationDetail, isBasic);
    }


    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, boolean isBasic) {
        return findBasePage(pageable, specificationDetail, isBasic, null, null);
    }


}
