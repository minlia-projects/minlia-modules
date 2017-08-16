package com.minlia.cloud.service;

import com.google.common.collect.Maps;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.data.batis.PublicUtil;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.QuerySpecifications;
import com.minlia.cloud.query.specification.batis.QueryUtil;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
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
 * Created by henry on 16/08/2017.
 */
@Slf4j
public abstract class AbstractReadOnlyService<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> implements ReadOnlyService<DAO,ENTITY, PK> {

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
        ENTITY entity = getOne(id);
        if (entity == null) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public ENTITY getOne(PK id) {
        return dao.findOne(id);
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
    public List<ENTITY> findAll(List<QueryCondition> queryConditions) {
        return this.findAll(Boolean.FALSE, queryConditions);
    }

    @Override
    public List<ENTITY> findAllBrief(List<QueryCondition> queryConditions) {
        return this.findAll(Boolean.TRUE, queryConditions);
    }

    @Override
    public List<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions) {
        SpecificationDetail<ENTITY> specificationDetail = new SpecificationDetail<ENTITY>();
        specificationDetail.andAll(queryConditions);
        specificationDetail.orderDesc("id");
        return this.findAll(isBrief, specificationDetail);
    }

    @Override
    public List<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail) {
//        return findAll(specificationDetail);
        return null;
    }

    @Override
    public List<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail) {
        return null;
    }

    @Override
    public List<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail) {
        return null;
    }

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
        return null;
    }

    @Override
    public Page<ENTITY> findAll(List<QueryCondition> queryConditions, Pageable pageable) {
        return this.findAll(Boolean.FALSE, queryConditions, pageable);
    }

    @Override
    public Page<ENTITY> findAllBriefly(List<QueryCondition> queryConditions, Pageable pageable) {
        return this.findAll(Boolean.TRUE, queryConditions, pageable);
    }

    @Override
    public Page<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions, Pageable pageable) {
        QueryCondition queryCondition = new QueryCondition();
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.buildSpecification("",
                clazz,
                queryCondition);
        if (PublicUtil.isNotEmpty(queryConditions)) {
            specificationDetail.andAll(queryConditions);
        }
        return findAll(isBrief, specificationDetail, pageable);
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
            String reason = String.format("%s无正确的参数化类型,请带参数使用. 如: UserQueryService<UserDao,User,Long>", this.getClass().getName());
            ApiPreconditions.checkNotNull(clazz, ApiCode.NOT_NULL, reason);
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
}
