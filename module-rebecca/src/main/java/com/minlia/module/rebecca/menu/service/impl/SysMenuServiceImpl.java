package com.minlia.module.rebecca.menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.enumeration.TreeNodeTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.menu.bean.SysMenuSro;
import com.minlia.module.rebecca.menu.entity.SysMenuEntity;
import com.minlia.module.rebecca.menu.mapper.SysMenuMapper;
import com.minlia.module.rebecca.menu.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenuEntity create(SysMenuSro sro) {
        return save(sro, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenuEntity update(SysMenuSro sro) {
        return save(sro, true);
    }

    @Override
    public Boolean disable(Long id) {
        SysMenuEntity entity = this.getById(id);
        entity.setDisFlag(!entity.getDisFlag());
        this.updateById(entity);
        return entity.getDisFlag();
    }

    @Override
    public Boolean hide(Long id) {
        SysMenuEntity entity = this.getById(id);
        entity.setHideFlag(!entity.getHideFlag());
        this.updateById(entity);
        return entity.getHideFlag();
    }

    private SysMenuEntity save(SysMenuSro sro, boolean isUpdate) {
        if (sro.getParentId() != 0) {
            SysMenuEntity parent = this.getById(sro.getParentId());
            ApiAssert.notNull(parent, SystemCode.Message.DATA_NOT_EXISTS);
            if (parent.getType().equals(TreeNodeTypeEnum.LEAF)) {
                parent.setType(TreeNodeTypeEnum.FOLDER);
                this.updateById(parent);
            }
        }
        SysMenuEntity entity = DozerUtils.map(sro, SysMenuEntity.class);
        this.saveOrUpdate(entity);
        return entity;
    }

}
