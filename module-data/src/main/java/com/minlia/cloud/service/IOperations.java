package com.minlia.cloud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface IOperations<T extends Serializable> {

    /**
     * findOne
     *
     * @param id
     * @return
     */
    public T findOne(final Long id);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    public List<T> findAll();

    Boolean exists(Long id);

    public Page<T> findAll(Pageable pageable);


    // create
    public T create(T resource);


    // update
    public T update(T resource);

    // delete
    public void delete(Long id);

    /**
     * delete all
     */
    public void deleteAll();

    // count
    public Long count();


}
