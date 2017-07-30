package com.minlia.cloud.data.jpa.support.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = false)
public interface IOperations<T extends Persistable> {

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
