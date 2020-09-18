package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.ArticleCategorySro;
import com.minlia.module.article.entity.SysArticleCategoryEntity;
import com.minlia.module.article.mapper.SysArticleCategoryMapper;
import com.minlia.module.article.service.SysArticleCategoryService;
import com.minlia.module.dozer.util.DozerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 文章类目 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleCategoryServiceImpl extends ServiceImpl<SysArticleCategoryMapper, SysArticleCategoryEntity> implements SysArticleCategoryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysArticleCategoryEntity saveOrUpdate(ArticleCategorySro sro) {
        if (sro.getParentId() != 0) {
            ApiAssert.state(this.count(Wrappers.<SysArticleCategoryEntity>lambdaQuery()
                    .eq(SysArticleCategoryEntity::getId, sro.getParentId())
                    .eq(SysArticleCategoryEntity::getDisFlag, false)) > 0, SystemCode.Message.DATA_NOT_EXISTS);
            this.update(Wrappers.<SysArticleCategoryEntity>lambdaUpdate().set(SysArticleCategoryEntity::getIsLeaf, false).eq(SysArticleCategoryEntity::getId, sro.getParentId()));
        }
        SysArticleCategoryEntity entity = DozerUtils.map(sro, SysArticleCategoryEntity.class);
        this.saveOrUpdate(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disable(Long id) {
        SysArticleCategoryEntity entity = this.getById(id);
        entity.setDisFlag(!entity.getDisFlag());
        this.updateById(entity);
        return entity.getDisFlag();
    }

    @Override
    public boolean delete(Long id) {
        SysArticleCategoryEntity entity = this.getById(id);
        if (entity.getParentId() != 0) {
            //判断父级是否还有子集
            long childrenCount = this.count(Wrappers.<SysArticleCategoryEntity>lambdaQuery().eq(SysArticleCategoryEntity::getParentId, entity.getParentId()));
            if (childrenCount == 0) {
                //更新节点为叶
                this.update(Wrappers.<SysArticleCategoryEntity>lambdaUpdate().set(SysArticleCategoryEntity::getIsLeaf, true).eq(SysArticleCategoryEntity::getId, entity.getParentId()));
            }
        }
        return this.removeById(id);
    }

    @Override
    public List<Long> selectCategoryIdsById(Long id) {
        List<Long> categoryIds = Lists.newArrayList();
        categoryIds.add(id);
        do {
            SysArticleCategoryEntity entity = this.getById(id);
            if (null != entity) {
                id = entity.getParentId();
                categoryIds.add(entity.getParentId());
            } else {
                id = null;
            }
        } while (null != id);
        Collections.reverse(categoryIds);
        return categoryIds;
    }

}