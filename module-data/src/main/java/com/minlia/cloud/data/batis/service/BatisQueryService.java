//package com.minlia.cloud.data.batis.service;
//
//import com.minlia.cloud.body.query.QueryOperator;
//import com.minlia.cloud.dao.BatisDao;
//import com.minlia.cloud.query.specification.batis.QueryCondition;
//import com.minlia.cloud.query.specification.batis.SpecificationDetail;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Persistable;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Created by will on 8/8/17.
// */
//@Transactional(readOnly = true)
//
//public interface BatisQueryService<REPOSITORY extends BatisDao<ENTITY, PK>,
//        ENTITY extends Persistable<PK>, PK extends Serializable> {
//
//
//    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited, String selectStatement, String countStatement);
//
//    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, Boolean isBasicPropertyLimited);
//
//    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited);
//
//    public List<ENTITY> findAll(List<QueryCondition> authQueryConditions, Boolean isBasicPropertyLimited);
//
//    public List<ENTITY> findAll(SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited);
//
//
////    public Pageable constructPageable(Pageable pageable);
////
////    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, Boolean isBasicPropertyLimited);
////
////    public Page<ENTITY> findAll(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited);
////
////
////    /**
////     * 动态分页查询
////     *
////     * @param pageable               分页对象
////     * @param specificationDetail    动态条件对象
////     * @param isBasicPropertyLimited 是否关联对象查询
////     * @param selectStatement        自定义数据集合sql名称
////     * @param countStatement         自定义数据总数sql名称
////     * @return
////     */
////
////    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited, String selectStatement, String countStatement);
////
////    public List<ENTITY> findAll(List<QueryCondition> conditions, Boolean isBasicPropertyLimited);
////
////    @Transactional(readOnly = true)
////    public List<ENTITY> findAll(SpecificationDetail specificationDetail, Boolean isBasicPropertyLimited);
////
////
////    public boolean doCheckWithEntity(ENTITY entity, Map<String, QueryOperator> maps);
////
////    public boolean doCheckByProperty(ENTITY entity);
////
////    public boolean doCheckByPK(ENTITY entity);
////
////    public Iterable<ENTITY> save(Iterable<ENTITY> entitys);
////
////    public Iterable<ENTITY> saveIgnoreNull(Iterable<ENTITY> entitys);
////
////    public ENTITY saveIgnoreNull(ENTITY entity);
////
////    public ENTITY save(ENTITY entity);
////
////
////    @Transactional(readOnly = true)
////    public ENTITY findOne(PK id);
////
////    @Transactional(readOnly = true)
////    public Optional<ENTITY> findOneById(PK id);
////
////
////    public ENTITY findOne(Map<String, Object> paramsMap, String... columns);
////
////    public List<ENTITY> findAll(Map<String, Object> paramsMap, String... columns);
////
////    public List<ENTITY> findAll(Sort sort, Map<String, Object> paramsMap, String... columns);
////
////    public Page<ENTITY> findAll(Pageable pageable, Map<String, Object> paramsMap, String... columns);
////
////    public Long countBasicAll(Map<String, Object> paramsMap);
////
////
////    /**
////     * 动态集合查询
////     *
////     * @param specificationDetail 动态条件对象
////     * @return
////     */
////    @Transactional(readOnly = true)
////    public List<ENTITY> findAll(SpecificationDetail specificationDetail);
//
//
//}
