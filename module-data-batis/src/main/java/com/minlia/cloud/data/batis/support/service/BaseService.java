package com.minlia.cloud.data.batis.support.service;

import com.minlia.cloud.body.query.PageableResponseBody;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by will on 7/29/17.
 * update comments by cqqian 7/29/17.
 */
@Transactional
public interface BaseService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractAuditableEntity, PK extends Serializable> {


    /**
     * 批量保存实体
     *
     * @param entities
     * @return
     */
    public Iterable<ENTITY> save(Iterable<ENTITY> entities);

    /**
     * 批量保存，忽略空值
     *
     * @param entities
     * @return
     */
    public Iterable<ENTITY> saveIgnoreNull(Iterable<ENTITY> entities);

    /**
     * 保存实体忽略空值
     *
     * @param entity
     * @return
     */
    public ENTITY saveIgnoreNull(ENTITY entity);

    /**
     * 保存一个实体
     *
     * @param entity
     * @return
     */
    public ENTITY save(ENTITY entity);

    /**
     * 查找实体
     *
     * @param id
     * @return
     */
    public ENTITY findOne(PK id);

    /**
     * 根据具体的列查询
     *
     * @param paramsMap
     * @param columns
     * @return
     */
    public ENTITY findOne(Map<String, Object> paramsMap, String... columns);

    /**
     * 根据查询条件查询实体
     *
     * @param paramsMap
     * @param columns
     * @return
     */
    public List<ENTITY> findAll(Map<String, Object> paramsMap, String... columns);

    /**
     * 根据查询条件和排序条件进行查询
     *
     * @param sort
     * @param paramsMap
     * @param columns
     * @return
     */
    public List<ENTITY> findAll(Sort sort, Map<String, Object> paramsMap, String... columns);

    /**
     * 根据查询条件和排序条件、分页条件查询
     *
     * @param pageable
     * @param paramsMap
     * @param columns
     * @return
     */
    public Page<ENTITY> findAll(Pageable pageable, Map<String, Object> paramsMap, String... columns);


    /**
     * 统计查询查询出的数据条数
     *
     * @param paramsMap
     * @return
     */
    public Long countBasicAll(Map<String, Object> paramsMap);

    /**
     * 根据搜索条件查询
     *
     * @param specificationDetail
     * @return
     */
    public List<ENTITY> findAll(SpecificationDetail specificationDetail);

    /**
     * 根据搜索条件、不存在的实体进行搜索
     *
     * @param pm
     * @param specificationDetail
     * @param isBasic
     * @return
     */
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, boolean isBasic);

    /**
     * 动态分页查询(自定义)
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, String selectStatement, String countStatement);

    /**
     * 动态分页查询
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @return
     */
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic);

    /**
     * 动态分页查询
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @param isBasic             是否关联对象查询
     * @param selectStatement     自定义数据集合sql名称
     * @param countStatement      自定义数据总数sql名称
     * @return
     */
    public PageableResponseBody<ENTITY> findBasePage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement);

    /**
     * 动态分页查询
     *
     * @param pm                  分页对象
     * @param specificationDetail 动态条件对象
     * @return
     */
    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm, SpecificationDetail<ENTITY> specificationDetail);


    /**
     * 根据多个属性进行查询，检查实体是否存在，不存在则返回Boolean.TRUE
     *
     * @param entity
     * @param maps
     * @return
     */
    public Boolean doCheckWithEntity(ENTITY entity, Map<String, QueryOperator> maps);

    /**
     * 检查实体是否已经删除，已删除则返回Boolean.TRUE
     *
     * @param entity
     * @return
     */
    public Boolean doCheckByProperty(ENTITY entity);

    /**
     * 检查实体是否已经删除，已删除则返回Boolean.TRUE
     *
     * @param entity
     * @return
     */
    public Boolean doCheckByPK(ENTITY entity);


}
