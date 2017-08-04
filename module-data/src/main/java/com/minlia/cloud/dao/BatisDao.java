package com.minlia.cloud.dao;

import com.minlia.cloud.query.body.ApiSearchRequestBody;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by will on 8/3/17.
 */
public interface BatisDao<ENTITY extends Persistable<PK>, PK extends Serializable> {

    Page<ENTITY> findAll(ApiSearchRequestBody body, Pageable pageable);
    List<ENTITY> findAll(ApiSearchRequestBody body);

    Page<ENTITY> findAll(SpecificationDetail<T> specification, Pageable pageable);
    List<ENTITY> findAll(SpecificationDetail<T> specification);

}
