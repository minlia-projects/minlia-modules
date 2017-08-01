package com.minlia.cloud.data.batis.support.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.query.TreeQuery;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractStatefulEntity;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractTreeEntity;
import com.minlia.cloud.data.batis.support.repository.AbstractTreeRepository;
import com.minlia.cloud.utils.Assert;
import com.minlia.cloud.utils.PreconditionsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Slf4j
@Transactional
public abstract class AbstractTreeService<REPOSITORY extends AbstractTreeRepository<ENTITY, PK>, ENTITY extends AbstractTreeEntity, PK extends Serializable>
        extends AbstractRawService<REPOSITORY, ENTITY, PK> implements TreeService<REPOSITORY, ENTITY, PK> {


    /**
     * 逻辑删除
     *
     * @param id
     * @param likeParentIds
     * @return
     */
    public void deleteById(PK id, String likeParentIds, String lastModifiedBy) {
        operateStatusById(id, likeParentIds, AbstractStatefulEntity.FLAG_DELETE, lastModifiedBy);
    }

    public void operateStatusById(PK id, String likeParentIds, Integer status, String lastModifiedBy) {
        ENTITY entity = repository.findOneByIdOrParentIdsLike(id, likeParentIds);
        Assert.assertNotNull(entity, "无法查询到对象信息");
        entity.setStatus(status);
//        entity.setLastModifiedBy(lastModifiedBy);
//        entity.setLastModifiedDate(PublicUtil.getCurrentDate());
        repository.updateIgnoreNull(entity);

    }

    public ENTITY save(ENTITY entity) {
        String oldParentIds = entity.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
        if (entity.getParentId() != null) {
            //TODO
//            ENTITY parent = repository.findOne((Long)entity.getParentId());
//            if (parent == null || PreconditionsHelper.isEmpty(parent.getId()))
//                throw new ApiException("无法获取模块的父节点，插入失败");
//            if (parent != null) {
//                parent.setLeaf(false);
////                checkSave(parent);
//                repository.save(parent);
//            }
//            entity.setParentIds(PreconditionsHelper.toAppendStr(parent.getParentIds(), parent.getId(), ","));
        }

        if (PreconditionsHelper.isNotEmpty(entity.getId())) {
            Long count = repository.countByParentId(entity.getId());
            entity.setLeaf(count == null || count == 0 ? true : false);
        } else {
            entity.setLeaf(true);
        }
//        checkSave(entity);
        entity = repository.save(entity);
        // 更新子节点 parentIds
        List<ENTITY> list = repository.findAllByParentIdsLike(PreconditionsHelper.toAppendStr("%,", entity.getId(), ",%"));
        for (ENTITY e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
        }
        repository.save(list);
        log.debug("Save Information for T: {}", entity);
        return entity;
    }

    public List<Map<String, Object>> findTreeData(TreeQuery query) {
        String extId = query != null ? query.getExtId() : null, all = query != null ? query.getAll() : null;
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<ENTITY> list = repository.findAllByStatusNot(AbstractStatefulEntity.FLAG_DELETE);
        for (ENTITY e : list) {
            if ((PreconditionsHelper.isEmpty(extId)
                    || PreconditionsHelper.isEmpty(e.getParentIds()) || (PreconditionsHelper.isNotEmpty(extId) && !extId.equals(e.getId()) && e.getParentIds() != null && e.getParentIds().indexOf("," + extId + ",") == -1))
                    && (all != null || (all == null && AbstractStatefulEntity.FLAG_NORMAL.equals(e.getStatus())))) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", PreconditionsHelper.isEmpty(e.getParentId()) ? "0" : e.getParentId());
                map.put("name", e.getName());
                map.put("pIds", e.getParentIds());
                mapList.add(map);
            }
        }
        return mapList;
    }

    public ENTITY findTopByParentId(Long parentId) {
        List<ENTITY> tempList = repository.findTop1ByParentIdAndStatusNotOrderBySortDesc(parentId, AbstractStatefulEntity.FLAG_DELETE);
        return PreconditionsHelper.isNotEmpty(tempList) ? tempList.get(0) : null;
    }

    public Long countTopByParentId(Long parentId) {
        return repository.countByParentIdAndStatusNot(parentId, AbstractStatefulEntity.FLAG_DELETE);
    }


}
