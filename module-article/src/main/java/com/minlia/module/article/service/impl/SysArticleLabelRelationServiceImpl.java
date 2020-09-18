package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.article.entity.SysArticleLabelRelationEntity;
import com.minlia.module.article.mapper.SysArticleLabelRelationMapper;
import com.minlia.module.article.service.SysArticleLabelRelationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章标签中间表-多对多 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleLabelRelationServiceImpl extends ServiceImpl<SysArticleLabelRelationMapper, SysArticleLabelRelationEntity> implements SysArticleLabelRelationService {

    @Override
    public void saveWithArticleIdAndLabelIds(Long articleId, Set<Long> labelIds) {
        //根据文章删除关系
        this.removeById(Wrappers.<SysArticleLabelRelationEntity>lambdaQuery().eq(SysArticleLabelRelationEntity::getArticleId, articleId));
        //重新保存关系
        this.saveBatch(labelIds.stream().map(labelId -> SysArticleLabelRelationEntity.builder().articleId(articleId).labelId(labelId).build()).collect(Collectors.toList()));
    }

}