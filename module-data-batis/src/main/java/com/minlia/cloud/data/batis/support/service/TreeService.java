package com.minlia.cloud.data.batis.support.service;

import com.minlia.cloud.body.query.TreeQuery;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractTreeEntity;
import com.minlia.cloud.data.batis.support.repository.AbstractTreeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 7/29/17.
 */
public interface TreeService<REPOSITORY extends AbstractTreeRepository<ENTITY, PK>, ENTITY extends AbstractTreeEntity, PK extends Serializable> extends RawService<REPOSITORY, ENTITY, PK> {

    public void deleteById(PK id, String likeParentIds, String lastModifiedBy);

    public void operateStatusById(PK id, String likeParentIds, Integer status, String lastModifiedBy);

    public ENTITY save(ENTITY entity);

    @Transactional(readOnly = true)
    public List<Map<String, Object>> findTreeData(TreeQuery query);

    @Transactional(readOnly = true)
    public ENTITY findTopByParentId(Long parentId);

    @Transactional(readOnly = true)
    public Long countTopByParentId(Long parentId);

}
