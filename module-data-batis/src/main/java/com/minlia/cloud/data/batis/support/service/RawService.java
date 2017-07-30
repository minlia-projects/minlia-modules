package com.minlia.cloud.data.batis.support.service;

import com.minlia.cloud.body.query.PageableResponseBody;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractDataEntity;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by will on 7/29/17.
 */
public interface RawService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractDataEntity, PK extends Serializable> extends BaseService<REPOSITORY, ENTITY, PK> {

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(PK id);

    /**
     * 根据ids删除实体
     *
     * @param ids
     */
    public void delete(Iterable<PK> ids);

    /**
     * 删除数据库存存在的实体
     *
     * @param ids
     */
    public void deleteConfirmExists(Iterable<PK> ids);

    /**
     * 切换实体状态
     *
     * @param ids
     */
    public void lockOrUnLock(Iterable<PK> ids);

    /**
     * @param pm
     * @return
     */
    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm);

    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm, List<QueryCondition> queryConditions);

    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findPageQuery(PageableResponseBody<ENTITY> pm, List<QueryCondition> authQueryConditions, boolean isBasic);

    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasicPage(PageableResponseBody<ENTITY> pm, List<QueryCondition> queryConditions);


    @Transactional(readOnly = true)
    public PageableResponseBody<ENTITY> findBasicPage(PageableResponseBody<ENTITY> pm);

    /**
     * 根据主键更新实体状态
     *
     * @param id
     * @param status
     */
    public void updateStatusById(PK id, Integer status);

}
