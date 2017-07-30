/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.minlia.cloud.data.batis.support.repository;


import com.minlia.cloud.data.batis.support.persistence.entity.AbstractTreeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface AbstractTreeRepository<ENTITY extends AbstractTreeEntity, PK extends Serializable> extends AbstractRepository<ENTITY, PK> {

    Long countByParentId(Long parentId);

    Long countByParentIdAndStatusNot(Long parentId, Integer status);

    List<ENTITY> findAllByParentIdsLike(String parentIds);

    List<ENTITY> findAllByParentIdAndStatusNot(String parentId, Integer status);

    List<ENTITY> findAllByStatusNot(Integer status);

    List<ENTITY> findTop1ByParentIdAndStatusNotOrderBySortDesc(Long parentId, Integer status);

    ENTITY findOneByIdOrParentIdsLike(PK id, String likeParentIds);


}