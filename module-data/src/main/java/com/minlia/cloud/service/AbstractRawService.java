package com.minlia.cloud.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.query.specification.batis.BatisSpecifications;
import com.minlia.cloud.query.specification.batis.QueryUtil;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.jpa.JpaSpecifications;
import com.minlia.cloud.query.specification.sort.Order;
import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.cloud.utils.Assert;
import com.minlia.cloud.utils.PreconditionsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Transactional
@Slf4j
public abstract class AbstractRawService<REPOSITORY extends AbstractRepository, DAO extends MybatisRepository<ENTITY,PK>, ENTITY extends Persistable, PK extends Serializable> implements IRawService<ENTITY, PK> {


    @Autowired
    JpaSpecifications jpaSpecifications;
    @Autowired
    BatisSpecifications batisSpecifications;

    protected Class<ENTITY> clazz;

    @Autowired
    protected REPOSITORY repository;
    @Autowired
    protected DAO dao;


    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    /**
     * Enhanced for auto clazz found
     */
    public AbstractRawService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            clazz = (Class<ENTITY>) parameterizedType[2];

        }
    }

    // API

    // find - one

    @Override
    @Transactional(readOnly = true)
    public ENTITY findOne(final PK id) {
        return getRepository().findOne(id);
    }


    @Override
    public Boolean exists(PK id) {
        ENTITY t = this.findOne(id);
        if (null == t)
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ENTITY> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

    // save/create/persist

    public ENTITY beforeCreated(final ENTITY entity) {
        return entity;
    }

    public ENTITY afterCreated(ENTITY persistedEntity) {
        return persistedEntity;
    }

    @Override
    public ENTITY create(ENTITY entity) {
        Preconditions.checkNotNull(entity);
//        eventPublisher.publishEvent(new BeforeEntityCreateEvent<ENTITY>(this, clazz, entity));
        entity = beforeCreated(entity);
        ENTITY persistedEntity = getRepository().save(entity);
        persistedEntity = afterCreated(persistedEntity);
//        eventPublisher.publishEvent(new AfterEntityCreateEvent<ENTITY>(this, clazz, persistedEntity));
        return persistedEntity;
    }

    // update/merge

    @Override
    public ENTITY update(final ENTITY entity) {
        Preconditions.checkNotNull(entity);
        return getRepository().save(entity);
    }

    // delete

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    @Override
    public void delete(final PK id) {
        final ENTITY entity = getRepository().findOne(id);
//        ServicePreconditions.checkEntityExists(entity);
        getRepository().delete(entity);
    }

    public void delete(Iterator<PK> ids) {
        while (ids.hasNext()) {
            this.delete(ids.next());
        }
    }

    // count

    @Override
    public Long count() {
        return getRepository().count();
    }

    // template method

//    protected abstract JpaRepository<ENTITY, PK> getRepository();


    protected AbstractRepository<ENTITY, PK> getRepository() {
        return repository;
    }

    protected MybatisRepository<ENTITY, PK> getDao() {
        return dao;
    }

    protected JpaSpecificationExecutor<ENTITY> getSpecificationExecutor() {
        return (JpaSpecificationExecutor<ENTITY>) getRepository();
    }

    protected QueryDslPredicateExecutor<ENTITY> getQueryDslPredicateExecutor() {
        return (QueryDslPredicateExecutor<ENTITY>) getRepository();
    }


    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
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
            specificationDetail.setPersistentClass(clazz);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    Lists.newArrayList(BatisSpecifications.MYBITS_SEARCH_PARAMS_MAP),
                    paramsMap, true);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_CONDITION, new Object());


            List<ENTITY> ret;
            if(PreconditionsHelper.isNotEmpty(specificationDetail.getOrders())){
                ret=getDao().findAll(false, new Sort(toOrders(specificationDetail.getOrders())), paramsMap);
            }else{
                ret=getDao().findAll(false, paramsMap);
            }
            return ret;
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.buildException(e.getMessage());
        }
        return null;
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




    public Page<ENTITY> findPageByBody(com.minlia.cloud.query.specification.jpa.body.JpaApiSearchRequestBody body, Pageable pageable) {
        return getRepository().findAll(jpaSpecifications.buildSpecification(body),pageable);
    }

    public List<ENTITY> findListByBody(com.minlia.cloud.query.specification.batis.body.BatisApiSearchRequestBody body) {
        return this.findAll(batisSpecifications.buildSpecification(body));
    }











    public Page<ENTITY> findPage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail) {
        return findBasePage(pageable, specificationDetail, true);
    }

    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, boolean isBasic) {
        return findBasePage(pageable, specificationDetail, isBasic, null, null);
    }





    /**
     * 动态分页查询
     *
     * @param pageable                  分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement) {
        try {
            Map<String, Object> paramsMap = Maps.newHashMap();
            specificationDetail.setPersistentClass(clazz);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
                    specificationDetail.getAndQueryConditions(),
                    specificationDetail.getOrQueryConditions(),
                    null,
                    paramsMap, true);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            paramsMap.put(BatisSpecifications.MYBITS_SEARCH_CONDITION, new Object());

            Page ret;
            if(PreconditionsHelper.isNotEmpty(selectStatement) && PreconditionsHelper.isNotEmpty(countStatement)){

                ret= getDao().findAll(selectStatement, countStatement, pageable, paramsMap);

            }else{
                ret=getDao().findAll(isBasic, pageable, paramsMap);
            }

            return ret;
        } catch (Exception e) {
            log.error("error: {}", e);
            Assert.buildException(e.getMessage());
        }
        return null;
    }




}
