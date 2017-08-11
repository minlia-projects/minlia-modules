package com.minlia.cloud.data.batis.service;

import com.google.common.collect.Maps;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.data.batis.PublicUtil;
import com.minlia.cloud.query.specification.batis.BatisSpecifications;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.QueryUtil;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;

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
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
                    specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    null,
                    paramsMap, true);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_CONDITION, new Object());


            Boolean pageInstance = PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement);

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

    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, boolean isBasic) {
        QueryCondition queryCondition = QueryCondition.ne("enabled", false);
//        QueryCondition queryCondition1=QueryCondition.gt(BaseEntity.F_VERSION, 0);
        SpecificationDetail<ENTITY> specificationDetail = BatisSpecifications.buildSpecification("",
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
