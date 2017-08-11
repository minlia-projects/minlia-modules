package com.minlia.cloud.data.batis.service;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by will on 8/8/17.
 */
@Transactional(readOnly = true)
public interface BatisQueryService<REPOSITORY extends BatisDao<ENTITY, PK>,
        ENTITY extends Persistable<PK>, PK extends Serializable> {



    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement);

    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, boolean isBasic);

    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, boolean isBasic);


}
