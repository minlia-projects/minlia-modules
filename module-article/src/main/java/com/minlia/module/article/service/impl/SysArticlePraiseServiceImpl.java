package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.SysArticleEntity;
import com.minlia.module.article.entity.SysArticlePraiseEntity;
import com.minlia.module.article.mapper.SysArticlePraiseMapper;
import com.minlia.module.article.service.SysArticlePraiseService;
import com.minlia.module.article.service.SysArticleService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章赞、踩 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticlePraiseServiceImpl extends ServiceImpl<SysArticlePraiseMapper, SysArticlePraiseEntity> implements SysArticlePraiseService {

    private final SysArticleService sysArticleService;

    public SysArticlePraiseServiceImpl(SysArticleService sysArticleService) {
        this.sysArticleService = sysArticleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean praise(Long articleId) {
        long countArticle = sysArticleService.count(Wrappers.<SysArticleEntity>lambdaQuery().eq(SysArticleEntity::getId, articleId).eq(SysArticleEntity::getDisFlag, false));
        ApiAssert.state(countArticle == 1, SystemCode.Message.DATA_NOT_EXISTS);

        if (isPraised(articleId)) {
            this.remove(Wrappers.<SysArticlePraiseEntity>lambdaQuery().eq(SysArticlePraiseEntity::getArticleId, articleId).eq(SysArticlePraiseEntity::getOperator, SecurityContextHolder.getCurrentGuid()));
            return false;
        } else {
            this.save(SysArticlePraiseEntity.builder().articleId(articleId).operator(SecurityContextHolder.getCurrentGuid()).build());
            return true;
        }
    }

    @Override
    public boolean isPraised(Long articleId) {
        long countPraise = this.count(Wrappers.<SysArticlePraiseEntity>lambdaQuery().eq(SysArticlePraiseEntity::getArticleId, articleId).eq(SysArticlePraiseEntity::getOperator, SecurityContextHolder.getCurrentGuid()));
        return countPraise > 0;
    }

    @Override
    public Page page(int pageNumber, int pageSize) {
        List<Long> articleIds = this.list(Wrappers.<SysArticlePraiseEntity>lambdaQuery()
                .select(SysArticlePraiseEntity::getArticleId)
                .eq(SysArticlePraiseEntity::getOperator, SecurityContextHolder.getCurrentGuid()))
                .stream().map(data -> data.getArticleId()).collect(Collectors.toList());
        return sysArticleService.page(new Page(pageNumber, pageSize), Wrappers.<SysArticleEntity>lambdaQuery().in(SysArticleEntity::getId, articleIds));
    }

}