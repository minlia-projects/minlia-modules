//package com.minlia.cloud.data.batis.service;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.minlia.cloud.body.query.QueryOperator;
//import com.minlia.cloud.code.ApiCode;
//import com.minlia.cloud.dao.BatisDao;
//import com.minlia.cloud.data.batis.PublicUtil;
//import com.minlia.cloud.data.batis.Reflections;
//import com.minlia.cloud.entity.AbstractEntity;
//import com.minlia.cloud.query.specification.batis.QueryCondition;
//import com.minlia.cloud.query.specification.batis.QuerySpecifications;
//import com.minlia.cloud.query.specification.batis.QueryUtil;
//import com.minlia.cloud.query.specification.batis.SpecificationDetail;
//import com.minlia.cloud.query.specification.sort.Order;
//import com.minlia.cloud.utils.ApiPreconditions;
//import com.minlia.cloud.utils.Assert;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * Created by will on 8/7/17.
// */
//@Slf4j
//public class AbstractQueryService<REPOSITORY extends BatisDao<ENTITY, PK>,
//        ENTITY extends Persistable<PK>, PK extends Serializable> implements BatisQueryService<REPOSITORY, ENTITY, PK> {
//
//    public Class<ENTITY> persistentClass;
//
//    public AbstractQueryService() {
//        Class<?> c = getClass();
//        Type type = c.getGenericSuperclass();
//        if (type instanceof ParameterizedType) {
//            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
//            persistentClass = (Class<ENTITY>) parameterizedType[1];
//        }
//    }
//
//    public List<Sort.Order> toOrders(List<Order> orders) {
//        List<Sort.Order> orderList = Lists.newArrayList();
//        if (PublicUtil.isEmpty(orders)) {
//            return orderList;
//        }
//        for (Order order : orders) {
//            if (order == null) {
//                continue;
//            }
//            String property = order.getProperty();
//            Order.Direction direction = order.getDirection();
//            if (PublicUtil.isEmpty(property) || direction == null) {
//                continue;
//            }
//            orderList.add(new Sort.Order(direction.equals(Order.Direction.asc) ?
//                    Sort.Direction.ASC : Sort.Direction.DESC, property));
//        }
//        return orderList;
//    }
//
//
//    @Autowired
//    REPOSITORY repository;
//
//
//    /**
//     * 动态分页查询
//     *
//     * @param pageable               分页对象
//     * @param specificationDetail    动态条件对象
//     * @param isBasicPropertyLimited 是否关联对象查询
//     * @param selectStatement        自定义数据集合sql名称
//     * @param countStatement         自定义数据总数sql名称
//     * @return
//     */
//    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited, String selectStatement, String countStatement) {
//        try {
//            Map<String, Object> paramsMap = Maps.newHashMap();
//            specificationDetail.setPersistentClass(persistentClass);
//            String reason = String.format("%s无正确的参数化类型,请带参数使用. 如: UserQueryService<UserDao,User,Long>", this.getClass().getName());
//            ApiPreconditions.checkNotNull(persistentClass, ApiCode.NOT_NULL, reason);
//            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(
//                    specificationDetail.getAndQueryConditions(),
//                    specificationDetail.getOrQueryConditions(),
//                    null,
//                    paramsMap, true);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//
//            Boolean pageInstance = PublicUtil.isNotEmpty(selectStatement) && PublicUtil.isNotEmpty(countStatement);
//            pageable = constructPageable(pageable);
//            if (pageInstance) {
//                return repository.findAll(selectStatement, countStatement, pageable, paramsMap);
//            } else {
//                return repository.findAll(isBasicPropertyLimited, pageable, paramsMap);
//            }
//        } catch (Exception e) {
//            log.error("error: {}", e);
//            Assert.buildException(e.getMessage());
//        }
//        return null;
//    }
//
//    private Pageable constructPageable(Pageable pageable) {
//        Integer size = 15;
//        Integer page = 0;
//
//        Sort.Direction direction = Sort.Direction.DESC;
//
//        String property = "id";
//
//        if ((pageable.getPageSize() < 1000) || pageable.getPageSize() > 0) {
//            size = pageable.getPageSize();
//        }
//
//        if (pageable.getPageNumber() > -1) {
//            page = pageable.getPageNumber();
//        }
//
//        if (null != pageable.getSort()) {
//            return new PageRequest(page, size, pageable.getSort());
//        }
//
//        return new PageRequest(page, size, direction, property);
//
//    }
//
//    public Page<ENTITY> findPageQuery(Pageable pageable, List<QueryCondition> authQueryConditions, Boolean isBasicPropertyLimited) {
//        QueryCondition queryCondition = new QueryCondition();
//        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.buildSpecification("",
//                persistentClass,
//                queryCondition);
//        if (PublicUtil.isNotEmpty(authQueryConditions)) {
//            specificationDetail.orAll(authQueryConditions);
//        }
//        return findBasePage(pageable, specificationDetail, isBasicPropertyLimited);
//    }
//
//
//    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasicPropertyLimited) {
//        return findBasePage(pageable, specificationDetail, isBasicPropertyLimited, null, null);
//    }
//
//
//    public List<ENTITY> findAll(List<QueryCondition> conditions, Boolean isBasicPropertyLimited) {
//        SpecificationDetail<ENTITY> spd = new SpecificationDetail<ENTITY>();
//        spd.andAll(conditions);
//        spd.orderDesc("id");
//        return this.findAll(spd, isBasicPropertyLimited);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ENTITY> findAll(SpecificationDetail specificationDetail, Boolean isBasicPropertyLimited) {
//        try {
//            Map<String, Object> paramsMap = Maps.newHashMap();
//            specificationDetail.setPersistentClass(persistentClass);
//            String reason = String.format("%s无正确的参数化类型,请带参数使用. 如: UserQueryService<UserDao,User,Long>", this.getClass().getName());
//            ApiPreconditions.checkNotNull(persistentClass, ApiCode.NOT_NULL, reason);
//            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
//                    specificationDetail.getOrQueryConditions(),
//                    Lists.newArrayList(QuerySpecifications.MYBITS_SEARCH_PARAMS_MAP),
//                    paramsMap, true);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//
//            return PublicUtil.isNotEmpty(specificationDetail.getOrders()) ?
//                    repository.findAll(isBasicPropertyLimited, new Sort(toOrders(specificationDetail.getOrders())), paramsMap) :
//                    repository.findAll(isBasicPropertyLimited, paramsMap);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            Assert.buildException(e.getMessage());
//        }
//        return null;
//    }
//
//
//
//
//
//
//
//
//
//    public boolean doCheckWithEntity(ENTITY entity, Map<String, QueryOperator> maps) {
//        boolean rs = false;
//        if (PublicUtil.isNotEmpty(entity)) {
//            Map<String, Object> paramsMap = Maps.newHashMap();
//            List<QueryCondition> conditionList = QueryUtil.convertObjectToQueryCondition(entity, maps, persistentClass);
//            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(conditionList,
//                    null,
//                    paramsMap, true, true);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//            Long obj = countBasicAll(paramsMap);
//            if (obj == null || obj == 0) {
//                rs = true;
//            }
//        }
//        return rs;
//    }
//
//    public boolean doCheckByProperty(ENTITY entity) {
//        Map<String, QueryOperator> maps = Maps.newHashMap();
//        try {
////            maps.put("id", QueryOperator.ne);
////            maps.put(AbstractEntity.F_STATUS, QueryOperator.ne);
////            Reflections.setProperty(entity, BaseEntity.F_STATUS, GeneralEntity.FLAG_DELETE);
//        } catch (Exception e) {
//            log.error(e.toString());
//        }
//        return doCheckWithEntity(entity, maps);
//
//    }
//
//    public boolean doCheckByPK(ENTITY entity) {
//
//        boolean rs = false;
//        Map<String, QueryOperator> maps = Maps.newHashMap();
//        try {
////            maps.put(BaseEntity.F_STATUS, QueryOperator.ne);
////            Reflections.setProperty(entity, BaseEntity.F_STATUS, GeneralEntity.FLAG_DELETE);
//        } catch (Exception e) {
//            log.error(e.toString());
//        }
//        return doCheckWithEntity(entity, maps);
//    }
//
//    public Iterable<ENTITY> save(Iterable<ENTITY> entitys) {
//        entitys.forEach(item -> save(item));
//        return entitys;
//    }
//
//    public Iterable<ENTITY> saveIgnoreNull(Iterable<ENTITY> entitys) {
//        entitys.forEach(item -> saveIgnoreNull(item));
//        return entitys;
//    }
//
//    public ENTITY saveIgnoreNull(ENTITY entity) {
////		checkSave(entity);
//        entity = repository.saveIgnoreNull(entity);
//        log.debug("Save Information for Entity: {}", entity);
//        return entity;
//    }
//
//    public ENTITY save(ENTITY entity) {
////		checkSave(entity);
//        entity = repository.save(entity);
//        log.debug("Save Information for Entity: {}", entity);
//        return entity;
//    }
//
//
//    @Transactional(readOnly = true)
//    public ENTITY findOne(PK id) {
//        return repository.findOne(id);
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<ENTITY> findOneById(PK id) {
//        return Optional.of(repository.findOne(id));
//    }
//
//
//    public ENTITY findOne(Map<String, Object> paramsMap, String... columns) {
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        return repository.findOne(false, paramsMap, columns);
//    }
//
//    public List<ENTITY> findAll(Map<String, Object> paramsMap, String... columns) {
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        return repository.findAll(false, paramsMap, columns);
//    }
//
//    public List<ENTITY> findAll(Sort sort, Map<String, Object> paramsMap, String... columns) {
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        return repository.findAll(false, sort, paramsMap, columns);
//    }
//
//    public Page<ENTITY> findAll(Pageable pageable, Map<String, Object> paramsMap, String... columns) {
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        return repository.findAll(false, pageable, paramsMap, columns);
//
//    }
//
//    public Long countBasicAll(Map<String, Object> paramsMap) {
//        paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//        return repository.countAll(false, paramsMap);
//    }
//
//
//
//    /**
//     * 动态集合查询
//     *
//     * @param specificationDetail 动态条件对象
//     * @return
//     */
//    @Transactional(readOnly = true)
//    public List<ENTITY> findAll(SpecificationDetail specificationDetail) {
//        try {
//            Map<String, Object> paramsMap = Maps.newHashMap();
//            specificationDetail.setPersistentClass(persistentClass);
//            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(specificationDetail.getAndQueryConditions(),
//                    specificationDetail.getOrQueryConditions(),
//                    Lists.newArrayList(QuerySpecifications.MYBITS_SEARCH_PARAMS_MAP),
//                    paramsMap, true);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
//            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_CONDITION, new Object());
//
//            return PublicUtil.isNotEmpty(specificationDetail.getOrders()) ?
//                    repository.findAll(false, new Sort(toOrders(specificationDetail.getOrders())), paramsMap) :
//                    repository.findAll(false, paramsMap);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            Assert.buildException(e.getMessage());
//        }
//        return null;
//    }
//
//}
