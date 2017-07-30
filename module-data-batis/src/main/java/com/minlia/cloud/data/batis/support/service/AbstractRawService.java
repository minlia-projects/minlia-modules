package com.minlia.cloud.data.batis.support.service;

import com.minlia.cloud.body.query.PageableResponseBody;
import com.minlia.cloud.data.batis.support.persistence.QuerySpecifications;
import com.minlia.cloud.data.batis.support.persistence.SpecificationDetail;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractDataEntity;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractStatefulEntity;
import com.minlia.cloud.data.batis.support.query.QueryCondition;
import com.minlia.cloud.data.batis.support.repository.AbstractRepository;
import com.minlia.cloud.utils.Assert;
import com.minlia.cloud.utils.PreconditionsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
@Slf4j
public abstract class AbstractRawService<REPOSITORY extends AbstractRepository<ENTITY, PK>, ENTITY extends AbstractDataEntity, PK extends Serializable>
        extends AbstractBaseService<REPOSITORY, ENTITY, PK> implements RawService<REPOSITORY, ENTITY, PK> {

    public AbstractRawService() {
        super();
    }

    public void delete(PK id) {
        updateStatusById(id, AbstractStatefulEntity.FLAG_DELETE);
    }

    public void updateStatusById(PK id, Integer status) {
        ENTITY entity = repository.findOne(id);
        Assert.assertNotNull(entity, "无法查询到对象信息");
        entity.setStatus(status);
//		entity.setLastModifiedBy();
//		entity.setLastModifiedDate(PublicUtil.getCurrentDate());
        repository.updateIgnoreNull(entity);
    }

    /**
     * 逻辑删除 集合
     *
     * @param idList
     * @return
     */
    public void delete(Iterable<PK> idList) {
        for (PK id : idList) {
            delete(id);
        }
    }

    public void deleteConfirmExists(Iterable<PK> ids) {
        ids.forEach(id -> {
            ENTITY entity = repository.findOne(id);
            Assert.assertNotNull(entity, "对象 " + id + " 信息为空，删除失败");
            delete(id);
            log.debug("Deleted Entity: {}", entity);
        });
    }

    public void lockOrUnLock(Iterable<PK> ids) {
        ids.forEach(id -> {
            ENTITY entity = repository.findOne(id);
            Assert.assertNotNull(entity, "对象 " + id + " 信息为空，操作失败");
            updateStatusById(id, AbstractStatefulEntity.FLAG_NORMAL.equals(entity.getStatus()) ? AbstractStatefulEntity.FLAG_UNABLE : AbstractStatefulEntity.FLAG_NORMAL);
            log.debug("LockOrUnLock Entity: {}", entity);

        });
    }

    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm) {
        return findPageQuery(pm, null, false);
    }

    public PageableResponseBody<ENTITY> findBasicPage(PageableResponseBody<ENTITY> pm) {
        return findPageQuery(pm, null, true);
    }

    public PageableResponseBody<ENTITY> findPage(PageableResponseBody<ENTITY> pm, List<QueryCondition> queryConditions) {
        return findPageQuery(pm, queryConditions, false);
    }

    public PageableResponseBody<ENTITY> findBasicPage(PageableResponseBody<ENTITY> pm, List<QueryCondition> queryConditions) {
        return findPageQuery(pm, queryConditions, true);
    }

    public PageableResponseBody<ENTITY> findPageQuery(PageableResponseBody<ENTITY> pm, List<QueryCondition> authQueryConditions, boolean isBasic) {
        SpecificationDetail<ENTITY> specificationDetail = QuerySpecifications.buildSpecification(pm.getQueryConditionJson(),
                persistentClass,
                QueryCondition.ne(AbstractStatefulEntity.F_STATUS, AbstractStatefulEntity.FLAG_DELETE));
        if (PreconditionsHelper.isNotEmpty(authQueryConditions))
            specificationDetail.orAll(authQueryConditions);
//		specificationDetail.setPersistentClass();
        return findBasePage(pm, specificationDetail, isBasic);
    }


}
