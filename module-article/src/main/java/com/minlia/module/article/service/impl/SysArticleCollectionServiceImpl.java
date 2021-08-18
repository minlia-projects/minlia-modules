package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.SysArticleCollectionEntity;
import com.minlia.module.article.entity.SysArticleEntity;
import com.minlia.module.article.mapper.SysArticleCollectionMapper;
import com.minlia.module.article.service.SysArticleCollectionService;
import com.minlia.module.article.service.SysArticleService;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章收藏 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleCollectionServiceImpl extends ServiceImpl<SysArticleCollectionMapper, SysArticleCollectionEntity> implements SysArticleCollectionService {

    private final SysArticleService sysArticleService;

    public SysArticleCollectionServiceImpl(@Lazy SysArticleService sysArticleService) {
        this.sysArticleService = sysArticleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean collection(Long articleId) {
        long countArticle = sysArticleService.count(Wrappers.<SysArticleEntity>lambdaQuery().eq(SysArticleEntity::getId, articleId).eq(SysArticleEntity::getDisFlag, false).eq(SysArticleEntity::getDraftFlag, false));
        ApiAssert.state(countArticle == 1, SystemCode.Message.DATA_NOT_EXISTS);

        if (isCollected(articleId)) {
            this.remove(Wrappers.<SysArticleCollectionEntity>lambdaQuery().eq(SysArticleCollectionEntity::getArticleId, articleId).eq(SysArticleCollectionEntity::getOperator, SecurityContextHolder.getUid()));
            return false;
        } else {
            this.save(SysArticleCollectionEntity.builder().articleId(articleId).operator(SecurityContextHolder.getUid()).build());
            return true;
        }
    }

    @Override
    public boolean isCollected(Long articleId) {
        long countCollection = this.count(Wrappers.<SysArticleCollectionEntity>lambdaQuery().eq(SysArticleCollectionEntity::getArticleId, articleId).eq(SysArticleCollectionEntity::getOperator, SecurityContextHolder.getUid()));
        return countCollection > 0;
    }

    @Override
    public Page myPage(BaseQueryEntity qro) {
        List<Long> articleIds = this.list(Wrappers.<SysArticleCollectionEntity>lambdaQuery()
                .select(SysArticleCollectionEntity::getArticleId)
                .eq(SysArticleCollectionEntity::getOperator, SecurityContextHolder.getUid()))
                .stream().map(data -> data.getArticleId()).collect(Collectors.toList());
        return sysArticleService.page(qro.getPage(), Wrappers.<SysArticleEntity>lambdaQuery().in(SysArticleEntity::getId, articleIds));
    }

}