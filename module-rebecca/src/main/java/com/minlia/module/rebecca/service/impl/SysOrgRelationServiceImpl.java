package com.minlia.module.rebecca.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.rebecca.entity.SysOrgEntity;
import com.minlia.module.rebecca.entity.SysOrgRelationEntity;
import com.minlia.module.rebecca.mapper.SysOrgRelationMapper;
import com.minlia.module.rebecca.service.SysOrgRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织-关系 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Service
public class SysOrgRelationServiceImpl extends ServiceImpl<SysOrgRelationMapper, SysOrgRelationEntity> implements SysOrgRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysOrgEntity sysOrg) {
        //查询所有出先人并绑定关系
        List<SysOrgRelationEntity> relationList = list(
                Wrappers.<SysOrgRelationEntity>query().lambda().eq(SysOrgRelationEntity::getDescendant, sysOrg.getParentId()))
                .stream().map(relation -> {
                    relation.setDescendant(sysOrg.getId());
                    return relation;
                }).collect(Collectors.toList());
        //自己也要维护到关系表中
        relationList.add(SysOrgRelationEntity.builder().ancestor(sysOrg.getId()).descendant(sysOrg.getId()).build());
        this.saveBatch(relationList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysOrgEntity sysOrg) {
        //删除先人、子孙
        delete(sysOrg.getId());
        //重新绑定关系
        create(sysOrg);
    }

    @Override
    public void delete(Long orgId) {
        //删除先人、子孙
        remove(Wrappers.<SysOrgRelationEntity>lambdaQuery()
                .eq(SysOrgRelationEntity::getDescendant, orgId)
                .or().eq(SysOrgRelationEntity::getAncestor, orgId));
    }

}