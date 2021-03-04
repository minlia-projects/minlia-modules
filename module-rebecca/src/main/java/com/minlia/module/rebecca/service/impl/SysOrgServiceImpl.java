package com.minlia.module.rebecca.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.bean.SysOrganizationCro;
import com.minlia.module.rebecca.bean.SysOrganizationUro;
import com.minlia.module.rebecca.constant.SysOrgCode;
import com.minlia.module.rebecca.entity.SysOrgEntity;
import com.minlia.module.rebecca.entity.SysOrgRelationEntity;
import com.minlia.module.rebecca.mapper.SysOrgMapper;
import com.minlia.module.rebecca.service.SysOrgRelationService;
import com.minlia.module.rebecca.service.SysOrgService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrgEntity> implements SysOrgService {

    private final SysOrgRelationService sysOrgRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysOrgEntity create(SysOrganizationCro cro) {
        //校验父级ID是否存在
        if (!cro.getParentId().equals(NumberUtils.LONG_ZERO)) {
            ApiAssert.state(existsById(cro.getParentId()), SysOrgCode.Message.PARENT_NOT_EXISTS);
        }
        //保存组织
        SysOrgEntity sysOrg = DozerUtils.map(cro, SysOrgEntity.class);
        this.save(sysOrg);
        //保存组织关系
        sysOrgRelationService.create(sysOrg);
        return sysOrg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysOrganizationUro uro) {
        //校验父级ID是否存在
        if (!uro.getParentId().equals(NumberUtils.LONG_ZERO)) {
            ApiAssert.state(existsById(uro.getParentId()), SysOrgCode.Message.PARENT_NOT_EXISTS);
        }
        //更新组织
        SysOrgEntity sysOrg = DozerUtils.map(uro, SysOrgEntity.class);
        this.updateById(sysOrg);
        //更新组织关系
        sysOrgRelationService.update(sysOrg);
        return Boolean.TRUE;
    }

    @Override
    public boolean delete(Long id) {
        //获取所有的子孙
        List<Long> idList = sysOrgRelationService
                .list(Wrappers.<SysOrgRelationEntity>query().lambda().eq(SysOrgRelationEntity::getAncestor, id)).stream()
                .map(SysOrgRelationEntity::getDescendant).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(idList)) {
            this.removeByIds(idList);
        }
        //删除组织关系
        sysOrgRelationService.delete(id);
        return Boolean.TRUE;
    }

    boolean existsById(Long id) {
        return this.count(Wrappers.<SysOrgEntity>lambdaQuery().eq(SysOrgEntity::getId, id)) > 0;
    }

}