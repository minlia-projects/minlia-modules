package com.minlia.cloud.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Transactional
public abstract class AbstractRawService<T extends Persistable> implements IRawService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    // public AbstractRawService(final Class<T> clazzToSet) {
    // super();
    // clazz = clazzToSet;
    // }

    /**
     * Enhanced for auto clazz found
     */
    public AbstractRawService() {
        Type type = getClass().getGenericSuperclass();
        Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
        clazz = (Class<T>) parameterizedType[0];
    }

    // API

    // find - one

    @Override
    @Transactional(readOnly = true)
    public T findOne(final Long id) {
        return getRepository().findOne(id);
    }


    @Override
    public Boolean exists(Long id) {
        T t = this.findOne(id);
        if (null == t)
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }



    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

    // save/create/persist

    public T beforeCreated(final T entity) {
        return entity;
    }

    public T afterCreated(T persistedEntity) {
        return persistedEntity;
    }

    @Override
    public T create(T entity) {
        Preconditions.checkNotNull(entity);

//        eventPublisher.publishEvent(new BeforeEntityCreateEvent<T>(this, clazz, entity));
        entity = beforeCreated(entity);
        T persistedEntity = getRepository().save(entity);
        persistedEntity = afterCreated(persistedEntity);
//        eventPublisher.publishEvent(new AfterEntityCreateEvent<T>(this, clazz, persistedEntity));
        return persistedEntity;
    }

    // update/merge

    @Override
    public T update(final T entity) {
        Preconditions.checkNotNull(entity);
        return getRepository().save(entity);
    }

    // delete

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    @Override
    public void delete(final Long id) {
        final T entity = getRepository().findOne(id);
//        ServicePreconditions.checkEntityExists(entity);
        getRepository().delete(entity);
    }

    // count

    @Override
    public Long count() {
        return getRepository().count();
    }

    // template method

    protected abstract JpaRepository<T, Long> getRepository();

     protected JpaSpecificationExecutor<T> getSpecificationExecutor() {
        return (JpaSpecificationExecutor<T>) getRepository();
     }

     protected QueryDslPredicateExecutor<T> getQueryDslPredicateExecutor() {
        return (QueryDslPredicateExecutor<T>) getRepository();
     }


    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
