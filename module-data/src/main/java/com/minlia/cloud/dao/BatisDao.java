package com.minlia.cloud.dao;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Created by will on 8/3/17.
 */
public interface BatisDao<ENTITY extends Persistable<PK>, PK extends Serializable>{// extends BaseMapper<ENTITY>

//    Page<ENTITY> findAll(BatisApiSearchRequestBody body, Pageable pageable);
//    List<ENTITY> findAll(BatisApiSearchRequestBody body);
//
//    Page<ENTITY> findAll(SpecificationDetail<ENTITY> specification, Pageable pageable);
//    List<ENTITY> findAll(SpecificationDetail<ENTITY> specification);

}
