package com.minlia.cloud.service;

import com.minlia.cloud.dao.BatisDao;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by henry on 16/08/2017.
 */
@Transactional(readOnly = false)
public interface WriteOnlyService<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> {

    /**
     * save entity
     *
     * @param entity
     * @return
     */
    public ENTITY save(ENTITY entity);

    /**
     * update entity
     *
     * @param entity
     * @return
     */
    public ENTITY update(ENTITY entity);

    /**
     * delete by id
     *
     * @param id
     */
    public void delete(PK id);

    /**
     * delete all by entity list
     *
     * @param entities
     */
    public void deleteAll(Iterable<ENTITY> entities);

    /**
     * delete all
     */
    public void deleteAll();

}
