package com.minlia.module.rebecca.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.rebecca.user.bean.SysUserRelationQro;
import com.minlia.module.rebecca.user.bean.SysUserRelationVo;
import com.minlia.module.rebecca.user.entity.SysUserRelationEntity;
import com.minlia.module.rebecca.user.mapper.SysUserRelationMapper;
import com.minlia.module.rebecca.user.service.SysUserRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户-关系 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Service
public class SysUserRelationServiceImpl extends ServiceImpl<SysUserRelationMapper, SysUserRelationEntity> implements SysUserRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long ancestor, Long descendant) {
        //查询所有出先人并绑定关系
        List<SysUserRelationEntity> relationList = list(
                Wrappers.<SysUserRelationEntity>query().lambda().eq(SysUserRelationEntity::getDescendant, ancestor))
                .stream().map(relation -> {
                    relation.setDescendant(descendant);
                    relation.setLevel(relation.getLevel() + 1);
                    return relation;
                }).collect(Collectors.toList());
        //自己也要维护到关系表中
        relationList.add(SysUserRelationEntity.builder().ancestor(descendant).descendant(descendant).level(0).build());
        this.saveBatch(relationList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserRelationEntity entity) {
        //删除先人、子孙
        delete(entity.getDescendant());
        //重新绑定关系
        create(entity.getAncestor(), entity.getDescendant());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long uid) {
        //删除先人、子孙
        remove(Wrappers.<SysUserRelationEntity>lambdaQuery()
                .eq(SysUserRelationEntity::getDescendant, uid)
                .or().eq(SysUserRelationEntity::getAncestor, uid));
    }

    @Override
    public Page<SysUserRelationVo> detailsPage(SysUserRelationQro qro) {
        return this.baseMapper.detailsPage(qro.getPage(), qro);
    }

}