package com.minlia.cloud.service;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by henry on 16/08/2017.
 */
@Transactional(readOnly = true)
public interface ReadOnlyService<DAO extends BatisDao<ENTITY, PK>, ENTITY extends Persistable<PK>, PK extends Serializable> {

    /**
     * is entity exists
     *
     * @param id
     * @return
     */
    Boolean exists(PK id);

    /**
     * get one by id
     *
     * @param id
     * @return
     */
    public ENTITY getOne(final PK id);

    /**
     * find all
     *
     * @return
     */
    public List<ENTITY> findAll();

    /**
     * find all by id list
     *
     * @param ids
     * @return
     */
    public List<ENTITY> findAll(Iterable<PK> ids);

    /**
     * find all and sort
     *
     * @param sort
     * @return
     */
    public List<ENTITY> findAll(Sort sort);

    /**
     * findAll
     *
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(Pageable pageable);

    /**
     * countAll
     *
     * @return
     */
    public Long countAll();

    /**
     * find all by QueryCondition list
     *
     * @param queryConditions
     * @return
     */
    public List<ENTITY> findAll(List<QueryCondition> queryConditions);

    /**
     * find all briefly by QueryCondition list
     *
     * @param queryConditions
     * @return
     */
    public List<ENTITY> findAllBrief(List<QueryCondition> queryConditions);

    /**
     * find all briefly or not by QueryCondition list
     *
     * @param isBrief
     * @param queryConditions
     * @return
     */
    public List<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions);

    /**
     * find all by SpecificationDetail
     *
     * @param specificationDetail
     * @return
     */
    public List<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail);

    /**
     * find all briefly by SpecificationDetail
     *
     * @param specificationDetail
     * @return
     */
    public List<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail);

    /**
     * find all briefly or not by SpecificationDetail
     *
     * @param isBrief
     * @param specificationDetail
     * @return
     */
    public List<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail);

    /**
     * find all by ApiQueryRequestBody
     *
     * @param body
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(ApiQueryRequestBody body, Pageable pageable);

    /**
     * find all briefly by ApiQueryRequestBody
     *
     * @param body
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAllBriefly(ApiQueryRequestBody body, Pageable pageable);

    /**
     * find all briefly or not by ApiQueryRequestBody
     *
     * @param isBrief
     * @param body
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(Boolean isBrief, ApiQueryRequestBody body, Pageable pageable);

    /**
     * find all by QueryCondition list in pages
     *
     * @param queryConditions
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(List<QueryCondition> queryConditions, Pageable pageable);

    /**
     * find all briefly by QueryCondition list in pages
     *
     * @param queryConditions
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAllBriefly(List<QueryCondition> queryConditions, Pageable pageable);

    /**
     * find all briefly or not by QueryCondition list in pages
     *
     * @param isBrief
     * @param queryConditions
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(Boolean isBrief, List<QueryCondition> queryConditions, Pageable pageable);

    /**
     * find all by SpecificationDetail in pages
     *
     * @param specificationDetail
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail, Pageable pageable);

    /**
     * find all briefly by SpecificationDetail in pages
     *
     * @param specificationDetail
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail, Pageable pageable);

    /**
     * find all briefly or not by SpecificationDetail in pages
     *
     * @param isBrief
     * @param specificationDetail
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail, Pageable pageable);

    /**
     * find all by SpecificationDetail and select and count statements in pages
     *
     * @param specificationDetail
     * @param selectStatement
     * @param countStatement
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable);

    /**
     * 动态分页查询
     *
     * @param specificationDetail 动态条件对象
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @param pageable            分页对象
     * @return
     */
    public Page<ENTITY> findAllBriefly(SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable);

    /**
     * find all briefly or not by SpecificationDetail and select and count statements in pages
     *
     * @param isBrief
     * @param specificationDetail
     * @param selectStatement
     * @param countStatement
     * @param pageable
     * @return
     */
    public Page<ENTITY> findAll(Boolean isBrief, SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement, Pageable pageable);

}
