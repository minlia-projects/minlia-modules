package com.minlia.cloud.data.batis.support.service;

import com.google.common.collect.Maps;
import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.data.batis.support.persistence.QuerySpecifications;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractStatefulEntity;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import com.minlia.cloud.data.batis.support.util.QueryUtil;
import com.minlia.cloud.utils.PreconditionsHelper;
import com.minlia.cloud.utils.Reflections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
@Slf4j
public abstract class AbstractBaseService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractAuditableEntity, PK extends Serializable> extends AbstractReadonlyService<REPOSITORY,ENTITY,PK> implements BaseService<REPOSITORY, ENTITY, PK> {



    public Boolean doCheckWithEntity(ENTITY entity, Map<String, QueryOperator> maps) {
        boolean rs = false;
        if (PreconditionsHelper.isNotEmpty(entity)) {
            Map<String, Object> paramsMap = Maps.newHashMap();
            List<QueryCondition> conditionList = QueryUtil.convertObjectToQueryCondition(entity, maps, persistentClass);
            String sqlConditionDsf = QueryUtil.convertQueryConditionToStr(conditionList,
                    null,
                    paramsMap, true, true);
            paramsMap.put(QuerySpecifications.MYBITS_SEARCH_DSF, sqlConditionDsf);
            Long obj = countBasicAll(paramsMap);
            if (obj == null || obj == 0) {
                rs = true;
            }
        }
        return rs;
    }

    public Boolean doCheckByProperty(ENTITY entity) {
        Map<String, QueryOperator> maps = Maps.newHashMap();
        try {
            maps.put(AbstractStatefulEntity.F_ID, QueryOperator.ne);
            maps.put(AbstractStatefulEntity.F_STATUS, QueryOperator.ne);
            Reflections.setProperty(entity, AbstractStatefulEntity.F_STATUS, AbstractAuditableEntity.FLAG_DELETE);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return doCheckWithEntity(entity, maps);

    }

    public Boolean doCheckByPK(ENTITY entity) {

        boolean rs = false;
        Map<String, QueryOperator> maps = Maps.newHashMap();
        try {
            maps.put(AbstractStatefulEntity.F_STATUS, QueryOperator.ne);
            Reflections.setProperty(entity, AbstractStatefulEntity.F_STATUS, AbstractAuditableEntity.FLAG_DELETE);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return doCheckWithEntity(entity, maps);
    }

    public Iterable<ENTITY> save(Iterable<ENTITY> entities) {
        entities.forEach(item -> save(item));
        return entities;
    }

    public Iterable<ENTITY> saveIgnoreNull(Iterable<ENTITY> entities) {
        entities.forEach(item -> saveIgnoreNull(item));
        return entities;
    }

    //	public void checkSave(T entity){
//		if(entity.isNew()){
//			entity.preInssert();
//		}else{
//			entity.preUpdate();
//		}
//	}x
    public ENTITY saveIgnoreNull(ENTITY entity) {
//		checkSave(entity);
        entity = repository.saveIgnoreNull(entity);
        log.debug("Save Information for Entity: {}", entity);
        return entity;
    }

    public ENTITY save(ENTITY entity) {
//		checkSave(entity);
        entity = repository.save(entity);
        log.debug("Save Information for Entity: {}", entity);
        return entity;
    }


    @Transactional(readOnly = true)
    public ENTITY findOne(PK id) {
        return repository.findOne(id);
    }



}
