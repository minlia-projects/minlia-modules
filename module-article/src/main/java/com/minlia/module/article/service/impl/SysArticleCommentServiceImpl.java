package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.ArticleCommentCro;
import com.minlia.module.article.entity.SysArticleCommentEntity;
import com.minlia.module.article.entity.SysArticleEntity;
import com.minlia.module.article.mapper.SysArticleCommentMapper;
import com.minlia.module.article.service.SysArticleCommentService;
import com.minlia.module.article.service.SysArticleService;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章评论 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleCommentServiceImpl extends ServiceImpl<SysArticleCommentMapper, SysArticleCommentEntity> implements SysArticleCommentService {

    private final SysArticleService sysArticleService;

    public SysArticleCommentServiceImpl(@Lazy SysArticleService sysArticleService) {
        this.sysArticleService = sysArticleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysArticleCommentEntity create(ArticleCommentCro cro) {
        long countArticle = sysArticleService.count(Wrappers.<SysArticleEntity>lambdaQuery().eq(SysArticleEntity::getId, cro.getArticleId()).eq(SysArticleEntity::getDisFlag, false).eq(SysArticleEntity::getDraftFlag, false));
        ApiAssert.state(countArticle == 1, SystemCode.Message.DATA_NOT_EXISTS);

        SysArticleCommentEntity entity = DozerUtils.map(cro, SysArticleCommentEntity.class);
        entity.setOperator(SecurityContextHolder.getUid());
        this.save(entity);
        return entity;
    }

    @Override
    public boolean isCommented(Long articleId) {
        long countPraise = this.count(Wrappers.<SysArticleCommentEntity>lambdaQuery().eq(SysArticleCommentEntity::getArticleId, articleId).eq(SysArticleCommentEntity::getOperator, SecurityContextHolder.getUid()));
        return countPraise > 0;
    }

    @Override
    public Page myPage(int pageNumber, int pageSize) {
        List<Long> articleIds = this.list(Wrappers.<SysArticleCommentEntity>lambdaQuery()
                .select(SysArticleCommentEntity::getArticleId)
                .eq(SysArticleCommentEntity::getOperator, SecurityContextHolder.getUid()))
                .stream().map(data -> data.getArticleId()).collect(Collectors.toList());
        return sysArticleService.page(new Page(pageNumber, pageSize), Wrappers.<SysArticleEntity>lambdaQuery().in(SysArticleEntity::getId, articleIds));
    }

}
