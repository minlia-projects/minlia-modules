package com.minlia.cloud.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.data.batis.PublicUtil;
import com.minlia.cloud.query.specification.batis.QuerySpecifications;
import com.minlia.cloud.query.specification.batis.QueryUtil;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by henry on 16/08/2017.
 */
@Slf4j
@Transactional(readOnly = true)
public class AbstractReadOnlyService<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> implements ReadOnlyService<DAO, ENTITY, PK> {

    public Class<ENTITY> clazz;

    @Autowired
    protected DAO dao;

    public AbstractReadOnlyService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            clazz = (Class<ENTITY>) parameterizedType[2];
        }
    }


    @Override
    public Boolean exists(PK id) {
        ENTITY entity = findOne(id);
        if (entity == null) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public ENTITY findOne(PK id) {
        return dao.findOne(id);
    }

    @Override
    public ENTITY findOneBriefly(PK id) {
        return dao.findBasicOne(id);
    }

    @Override
    public List<ENTITY> findAll() {
        return dao.findAll();
    }

    @Override
    public List<ENTITY> findAll(Iterable<PK> ids) {
        return dao.findAll(ids);
    }

    @Override
    public List<ENTITY> findAll(Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public Page<ENTITY> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public Long countAll() {
        return dao.count();
    }


    @Override
    public List<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail) {
        return findAll(true, specificationDetail);
    }

    public List<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail) {
        return findAll(false, specificationDetail);
    }

    @Override
    public List<ENTITY> findAll(ApiQueryRequestBody body) {
        return this.findAll(false, body);
    }

    @Override
    public List<ENTITY> findAllBriefly(ApiQueryRequestBody body) {
        return findAll(true, body);
    }

    /**
     * 合并前后端传递过来的Conditions并使用Specification查询
     *
     * @param isBrief
     * @param body
     * @return
     */
    @Override
    public List<ENTITY> findAll(Boolean isBrief, ApiQueryRequestBody body) {
        SpecificationDetail<ENTITY> spec = QuerySpecifications.buildSpecification(body);
        return this.findAll(isBrief, spec);
    }


//    @Override
//    public List<ENTITY> findAll(List<QueryCondition> queryConditions) {
//        return this.findAll(Boolean.FALSE, queryConditions);
//    }
//
//    @Override
//    public List<ENTITY> findAllBrief(List<QueryCondition> queryConditions) {
//        return this.findAll(Boolean.TRUE, queryConditions);
//    }
//
//    @Override
//    public List<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions) {
//        SpecificationDetail<ENTITY> specificationDetail = new SpecificationDetail<ENTITY>();
//        specificationDetail.andAll(queryConditions);
//        specificationDetail.orderDesc("id");
//        return this.findAll(isBrief, specificationDetail);
//    }
//    @Override
//    public Page<ENTITY> findAll(List<QueryCondition> queryConditions, Pageable pageable) {
//        return this.findAll(Boolean.FALSE, queryConditions, pageable);
//    }
//
//    @Override
//    public Page<ENTITY> findAllBriefly(List<QueryCondition> queryConditions, Pageable pageable) {
//        return this.findAll(Boolean.TRUE, queryConditions, pageable);
//    }
//
//    @Override
//    public Page<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions, Pageable pageable) {
//        QueryCondition queryCondition = new QueryCondition();
//        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.buildSpecification("",
//                clazz,
//                queryCondition);
//        if (PublicUtil.isNotEmpty(queryConditions)) {
//            specificationDetail.andAll(queryConditions);
//        }
//        return findAll(isBrief, specificationDetail, pageable);
//    }


    @Override
    public Page<ENTITY> findAll(ApiQueryRequestBody body, Pageable pageable) {
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.bySearchQueryCondition(body.getConditions());
        return this.findAll(specificationDetail, pageable);
    }

    @Override
    public Page<ENTITY> findAllBriefly(ApiQueryRequestBody body, Pageable pageable) {
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.bySearchQueryCondition(body.getConditions());
        return this.findAllBriefly(specificationDetail, pageable);
    }

    @Override
    public Page<ENTITY> findAll(Boolean isBrief, ApiQueryRequestBody body, Pageable pageable) {
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.bySearchQueryCondition(body.getConditions());
        return findAll(isBrief, specificationDetail, null, null, pageable);
    }

    @Override
    public Page<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail, Pageable pageable) {
        return this.findAll(specificationDetail, null, null, pageable);
    }

    @Override
    public Page<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail, Pageable pageable) {
        return findAllBriefly(specificationDetail, null, null, pageable);
    }

    @Override
    public Page<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail, Pageable pageable) {
        return findAll(isBrief, specificationDetail, null, null, pageable);
    }

    @Override
    public Page<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable) {
        return findAll(Boolean.FALSE, specificationDetail, selectStatement, countStatement, pageable);
    }

    @Override
    public Page<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable) {
        return findAll(Boolean.TRUE, specificationDetail, selectStatement, countStatement, pageable);
    }

    public Page<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(clazz);
            String reason = String.format("%s无正确的参数化类型,请带参数使用. 如: UserReadOnlyService<UserDao,User,Long>", this.getClass().getName());
            ApiPreconditions.checkNotNull(clazz, ApiCode.NOT_NULL, reason);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
                    specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    null,
                    paramsMap, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
            Boolean pageInstance = PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement);
            pageable = QuerySpecifications.constructPageable(pageable);
            if (pageInstance) {
                return dao.findAll(selectStatement, countStatement, pageable, paramsMap);
            } else {
                return dao.findAll(isBrief, pageable, paramsMap);
            }
        } catch (Exception e) {
            log.error("error: {}", e);
            Assert.buildException(e.getMessage());
        }
        return null;
    }

    public List<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(clazz);
            String reason = String.format("%s无正确的参数化类型,请带参数使用. 如: UserReadOnlyService<UserDao,User,Long>", this.getClass().getName());
            ApiPreconditions.checkNotNull(clazz, ApiCode.NOT_NULL, reason);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    Lists.newArrayList(QuerySpecifications.MYBITS_SEARCH_PARAMS_MAP),
                    paramsMap, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
            Boolean ordered = PublicUtil.isNotEmpty(specificationDetail.getOrders());
            if (ordered) {
                return dao.findAll(isBrief, new Sort(QuerySpecifications.toOrders(specificationDetail.getOrders())), paramsMap);
            } else {
                return dao.findAll(isBrief, paramsMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.buildException(e.getMessage());
        }
        return null;
    }

}
