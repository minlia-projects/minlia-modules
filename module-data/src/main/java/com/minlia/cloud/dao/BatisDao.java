package com.minlia.cloud.dao;

import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.BatisApiSearchRequestBody;
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

    Page<ENTITY> findAll(BatisApiSearchRequestBody body, Pageable pageable);
    List<ENTITY> findAll(BatisApiSearchRequestBody body);

    Page<ENTITY> findAll(SpecificationDetail<T> specification, Pageable pageable);
    List<ENTITY> findAll(SpecificationDetail<T> specification);

}
