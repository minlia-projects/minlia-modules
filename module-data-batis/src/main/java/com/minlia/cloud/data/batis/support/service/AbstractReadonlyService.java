package com.minlia.cloud.data.batis.support.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.query.Order;
import com.minlia.cloud.body.query.PageableResponseBody;
import com.minlia.cloud.data.batis.support.persistence.QuerySpecifications;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import com.minlia.cloud.data.batis.support.query.ApiSearchRequestBody;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import com.minlia.cloud.data.batis.support.util.QueryUtil;
import com.minlia.cloud.utils.Assert;
import com.minlia.cloud.utils.PreconditionsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 8/1/17.
 */
@Slf4j
public abstract class AbstractReadonlyService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractAuditableEntity, PK extends Serializable> implements ReadonlyService<REPOSITORY, ENTITY, PK> {

    @Autowired
    protected REPOSITORY repository;

    protected Class<ENTITY> persistentClass;

    public AbstractReadonlyService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            persistentClass = (Class<ENTITY>) parameterizedType[1];
//            repository =  (REPOSITORY)ContextHolder.getContext().getBean(parameterizedType[0].getClass().getName(),repository);
        }
    }


    public ENTITY findOne(Map<String, Object> paramsMap, String... columns) {
        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
        return repository.findOne(false, paramsMap, columns);
    }

    public List<ENTITY> findAll(Map<String, Object> paramsMap, String... columns) {
        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
        return repository.findAll(false, paramsMap, columns);
    }

    public List<ENTITY> findAll(Sort sort, Map<String, Object> paramsMap, String... columns) {
        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
        return repository.findAll(false, sort, paramsMap, columns);
    }

    public Page<ENTITY> findAll(Pageable pageable, Map<String, Object> paramsMap, String... columns) {
        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
        return repository.findAll(false, pageable, paramsMap, columns);

    }

    public Long countBasicAll(Map<String, Object> paramsMap) {
        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
        return repository.countAll(false, paramsMap);
    }

    public List<Sort.Order> toOrders(List<Order> orders) {
        List<Sort.Order> orderList = Lists.newArrayList();
        if (PreconditionsHelper.isEmpty(orders)) {
            return orderList;
        }
        for (Order order : orders) {
            if (order == null) {
                continue;
            }
            String property = order.getProperty();
            Order.Direction direction = order.getDirection();
            if (PreconditionsHelper.isEmpty(property) || direction == null) {
                continue;
            }
            orderList.add(new Sort.Order(direction.equals(Order.Direction.asc) ?
                    Sort.Direction.ASC : Sort.Direction.DESC, property));
        }
        return orderList;
    }


    /**
     * 动态集合查询
     *
     * @param specificationDetail 动态条件对象
     * @return
     */
    @Transactional(readOnly = true)
    public List<ENTITY> findAll(SpecificationDetail specificationDetail) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(persistentClass);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    Lists.newArrayList(QuerySpecifications.MYBITS_SEARCH_PARAMS_MAP),
                    paramsMap, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());

            return PreconditionsHelper.isNotEmpty(specificationDetail.getOrders()) ?
                    repository.findAll(false, new Sort(toOrders(specificationDetail.getOrders())), paramsMap) :
                    repository.findAll(false, paramsMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.buildException(e.getMessage());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, boolean isBasic) {
        return findBasePage(pm, specificationDetail, isBasic, null, null);
    }


    /**
     * 动态分页查询(自定义)
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement) {
        return findBasePage(pm, specificationDetail, null, selectStatement, countStatement);
    }

    /**
     * 动态分页查询
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @return
     */
    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic) {
        return findBasePage(pm, specificationDetail, isBasic, null, null);
    }

    /**
     * 动态分页查询
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(persistentClass);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
                    specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    null,
                    paramsMap, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
            pm.setPageInstance(PreconditionsHelper.isNotEmpty(selectStatement) && PreconditionsHelper.isNotEmpty(countStatement) ?
                    repository.findAll(selectStatement, countStatement, pm, paramsMap) : repository.findAll(isBasic, pm, paramsMap));
            return pm;
        } catch (Exception e) {
            log.error("error: {}", e);
            Assert.buildException(e.getMessage());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail) {
        return findBasePage(pm, specificationDetail, true);
    }


    @Autowired
    QuerySpecifications spec;


    /**
     * 动态集合查询
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ENTITY> findAll(ApiSearchRequestBody body,Pageable pageable) {
//        return this.findAll(spec.buildSpecification(body),pageable);
        return null;
    }



}
