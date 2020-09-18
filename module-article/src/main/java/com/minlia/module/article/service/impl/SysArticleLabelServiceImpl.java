package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.article.entity.SysArticleLabelEntity;
import com.minlia.module.article.entity.SysArticleLabelRelationEntity;
import com.minlia.module.article.mapper.SysArticleLabelMapper;
import com.minlia.module.article.service.SysArticleLabelRelationService;
import com.minlia.module.article.service.SysArticleLabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文章标签 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleLabelServiceImpl extends ServiceImpl<SysArticleLabelMapper, SysArticleLabelEntity> implements SysArticleLabelService {

    private final SysArticleLabelRelationService sysArticleLabelRelationService;

    public SysArticleLabelServiceImpl(SysArticleLabelRelationService sysArticleLabelRelationService) {
        this.sysArticleLabelRelationService = sysArticleLabelRelationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        //删除标签-文章关系
        sysArticleLabelRelationService.removeById(Wrappers.<SysArticleLabelRelationEntity>lambdaQuery().eq(SysArticleLabelRelationEntity::getLabelId, id));
        //删除标签
        return this.removeById(id);
    }

}
