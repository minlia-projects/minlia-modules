package com.minlia.cloud.service;

import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.repository.AbstractRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by will on 8/1/17.
 */
public interface ReadonlyService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractEntity, PK extends Serializable> {
    /**
     * 根据查询对象返回分页后的对象
     * @return
     */
//    public Page<ENTITY> findAll(ApiSearchRequestBody body, Pageable pageable);

    public List<ENTITY> findAll(SpecificationDetail specificationDetail);
//    public Page<ENTITY> findAll(ApiSearchRequestBody body);


}
