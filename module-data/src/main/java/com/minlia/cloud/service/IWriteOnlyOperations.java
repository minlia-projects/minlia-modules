package com.minlia.cloud.service;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Created by henry on 16/08/2017.
 */
public interface IWriteOnlyOperations<ENTITY extends Persistable, PK extends Serializable> {

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
