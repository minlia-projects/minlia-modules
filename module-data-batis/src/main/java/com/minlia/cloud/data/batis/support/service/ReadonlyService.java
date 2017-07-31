package com.minlia.cloud.data.batis.support.service;

import com.minlia.cloud.body.query.PageableResponseBody;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 8/1/17.
 */
public interface ReadonlyService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractAuditableEntity, PK extends Serializable> {

    public ENTITY findOne(Map<String, Object> paramsMap, String... columns);
    public List<ENTITY> findAll(Map<String, Object> paramsMap, String... columns);
    public List<ENTITY> findAll(Sort sort, Map<String, Object> paramsMap, String... columns);
    public Page<ENTITY> findAll(Pageable pageable, Map<String, Object> paramsMap, String... columns);
    public Long countBasicAll(Map<String, Object> paramsMap);
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement);
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic);
    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail);

}
