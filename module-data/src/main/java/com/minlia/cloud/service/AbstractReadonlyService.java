package com.minlia.cloud.service;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.query.Order;
import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.cloud.query.specification.batis.SpecificationDetail;
import com.minlia.cloud.repository.AbstractRepository;
import com.minlia.cloud.utils.PreconditionsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
public abstract class AbstractReadonlyService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractEntity, PK extends Serializable> implements ReadonlyService<REPOSITORY, ENTITY, PK> {

    @Autowired
    protected REPOSITORY repository;

    protected Class<ENTITY> persistentClass;

    public AbstractReadonlyService() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            persistentClass = (Class<ENTITY>) parameterizedType[1];
//            repository =  (REPOSITORY)ContextHolder.getContext().getBean(parameterizedType[0].getClass().getName(),repository);
        }
    }





    public List<Sort.Order> toOrders(List<Order> orders) {
        List<Sort.Order> orderList = Lists.newArrayList();
        if (PreconditionsHelper.isEmpty(orders)) {
            return orderList;
        }
        for (Order order : orders) {
            if (order == null) {
                continue;
            }
            String property = order.getProperty();
            Order.Direction direction = order.getDirection();
            if (PreconditionsHelper.isEmpty(property) || direction == null) {
                continue;
            }
            orderList.add(new Sort.Order(direction.equals(Order.Direction.asc) ?
                    Sort.Direction.ASC : Sort.Direction.DESC, property));
        }
        return orderList;
    }


    

    /**
     * 动态集合查询
     *
     * @param specificationDetail 动态条件对象
     * @return
     */
    @Transactional(readOnly = true)
    public List<ENTITY> findAll(SpecificationDetail specificationDetail) {
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
//            return PreconditionsHelper.isNotEmpty(specificationDetail.getOrders()) ?
//                    repository.findAll(false, new Sort(toOrders(specificationDetail.getOrders())), paramsMap) :
//                    repository.findAll(false, paramsMap);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            Assert.buildException(e.getMessage());
//        }
        return null;
    }

}