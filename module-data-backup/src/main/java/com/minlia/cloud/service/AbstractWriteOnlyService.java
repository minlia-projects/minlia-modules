package com.minlia.cloud.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Reflections;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by henry on 16/08/2017.
 */
@Slf4j
@Transactional(readOnly = false)
public abstract class AbstractWriteOnlyService<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> implements WriteOnlyService<DAO,ENTITY, PK> {

    public Class<ENTITY> clazz;

    @Autowired
    protected DAO dao;

    @Autowired
    private Mapper mapper;
    public AbstractWriteOnlyService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            clazz = (Class<ENTITY>) parameterizedType[2];
        }
    }


    @Override
    public ENTITY save(ENTITY entity) {
        return dao.save(entity);
    }

    @Override
    public ENTITY update(ENTITY entity) {
        return dao.updateIgnoreNull(entity);
    }

    @Override
    public ENTITY updateIgnoreNull(ENTITY entity) {
        return dao.updateIgnoreNull(entity);
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
