package com.minlia.cloud.dao;

import com.minlia.cloud.query.body.ApiSearchRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Created by will on 8/3/17.
 */
public interface BatisDao<ENTITY extends Persistable<PK>,PK extends Serializable> {

    Page<ENTITY> findPageByBody(ApiSearchRequestBody body, Pageable pageable);

//    List<T> findAll(boolean isBasic, Sort sort, Map<String, Object> paramsMap, String... columns);
//
//    Page<T> findAll(boolean isBasic, Pageable pageable, Map<String, Object> paramsMap, String... columns);


}
