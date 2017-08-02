package com.minlia.cloud.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IOperations<ENTITY extends Serializable> {

    // find - one

    ENTITY findOne(final long id);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<ENTITY> findAll();

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<ENTITY> findAllSorted(final String sortBy, final String sortOrder);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<ENTITY> findAllPaginated(final int page, final int size);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<ENTITY> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    // create

    ENTITY create(final ENTITY resource);

    // update

    void update(final ENTITY resource);

    // delete

    void delete(final long id);

    void deleteAll();

    // count

    long count();


}
