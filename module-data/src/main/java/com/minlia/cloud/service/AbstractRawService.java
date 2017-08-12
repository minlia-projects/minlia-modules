package com.minlia.cloud.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.minlia.cloud.query.specification.jpa.JpaSpecifications;
import com.minlia.cloud.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

@Transactional
@Slf4j
public abstract class AbstractRawService<REPOSITORY extends AbstractRepository, ENTITY extends Persistable, PK extends Serializable> implements IRawService<ENTITY, PK> {


    @Autowired
    JpaSpecifications jpaSpecifications;


    protected Class<ENTITY> clazz;

    @Autowired
    protected REPOSITORY repository;

//    @Autowired
//    protected DAO dao;


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
//        eventPublisher.publishEvent(new BeforeEntityCreateEvent<T>(this, clazz, entity));
        entity = beforeCreated(entity);
        ENTITY persistedEntity = getRepository().save(entity);
        persistedEntity = afterCreated(persistedEntity);
//        eventPublisher.publishEvent(new AfterEntityCreateEvent<T>(this, clazz, persistedEntity));
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

//    protected BatisDao getDao() {
//        return dao;
//    }

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


//    public Page<ENTITY> findPageByBody(ApiSearchRequestBody body, Pageable pageable) {
//        return getDao().findAll(batisSpecifications.buildSpecification(body), pageable);
//    }
////
//    public List<ENTITY> findListByBody(ApiSearchRequestBody body) {
//        return getDao().findAll(batisSpecifications.buildSpecification(body));
//    }



//    @Transactional(readOnly = true)
//    public PageModel<T> findBasePage(PageModel<T> pm, SpecificationDetail<T> specificationDetail, boolean isBasic) {
//        return findBasePage(pm, specificationDetail, isBasic, null, null);
//    }



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
//    @Transactional(readOnly = true)
//    public PageModel<T> findBasePage(PageModel<T> pm, SpecificationDetail<T> specificationDetail, Boolean isBasic, String selectStatement, String countStatement) {
//        try {
//            Map<String, Object> paramsMap = Maps.newHashMap();
//            specificationDetail.setPersistentClass(persistentClass);
//            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
//                    specificationDetail.getAndQueryConditions(),
//                    specificationDetail.getOrQueryConditions(),
//                    null,
//                    paramsMap, true);
//            paramsMap.put(DynamicSpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//            paramsMap.put(DynamicSpecifications.MYBITS_SEARCH_CONDITION, new Object());
//
//
//            Boolean pageInstance= PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement);
//
//            if(pageInstance) {
//                pm.setPageInstance(repository.findAll(selectStatement, countStatement, pm, paramsMap));
//            }else{
//                pm.setPageInstance(repository.findAll(isBasic, pm, paramsMap));
//            }
//
////            pm.setPageInstance(PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement) ?
////                    repository.findAll(selectStatement, countStatement, pm, paramsMap) :
////                    repository.findAll(isBasic, pm, paramsMap));
//            return pm;
//        } catch (Exception e) {
//            log.error("error: {}", e);
//            Assert.buildException(e.getMessage());
//        }
//        return null;
//    }




}
