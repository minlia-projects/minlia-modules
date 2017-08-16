package com.minlia.cloud.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.data.batis.PublicUtil;
import com.minlia.cloud.query.body.QueryRequestBody;
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
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 16/08/2017.
 */
@Slf4j
public class IWriteOnlyOperationsImpl<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> implements IWriteOnlyOperations<ENTITY, PK> {

    public Class<ENTITY> persistentClass;

    @Autowired
    protected DAO dao;

    @Override
    public ENTITY save(ENTITY entity) {
//        Preconditions.checkNotNull(entity);
//        eventPublisher.publishEvent(new BeforeEntityCreateEvent<T>(this, clazz, entity));
//        entity = beforeCreated(entity);
//        ENTITY persistedEntity = dao.save(entity);
//        persistedEntity = afterCreated(persistedEntity);
//        eventPublisher.publishEvent(new AfterEntityCreateEvent<T>(this, clazz, persistedEntity));
        return dao.save(entity);
    }

    @Override
    public ENTITY update(ENTITY entity) {
        return this.save(entity);
    }

    @Override
    public void delete(PK id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll(Iterable<ENTITY> entities) {
        dao.delete(entities);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
