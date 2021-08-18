package com.minlia.module.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.ArticleQro;
import com.minlia.module.article.bean.ArticleSro;
import com.minlia.module.article.bean.vo.ArticleVo;
import com.minlia.module.article.entity.*;
import com.minlia.module.article.mapper.SysArticleMapper;
import com.minlia.module.article.service.*;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.i18n.util.LocaleUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Service
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticleEntity> implements SysArticleService {

    private final SysArticleLabelService sysArticleLabelService;
    private final SysArticlePraiseService sysArticlePraiseService;
    private final SysArticleCommentService sysArticleCommentService;
    private final SysArticleCategoryService sysArticleCategoryService;
    private final SysArticleCollectionService sysArticleCollectionService;
    private final SysArticleLabelRelationService sysArticleLabelRelationService;

    public SysArticleServiceImpl(SysArticleLabelService sysArticleLabelService, SysArticlePraiseService sysArticlePraiseService, SysArticleCommentService sysArticleCommentService, SysArticleCategoryService sysArticleCategoryService, SysArticleCollectionService sysArticleCollectionService, SysArticleLabelRelationService sysArticleLabelRelationService) {
        this.sysArticleLabelService = sysArticleLabelService;
        this.sysArticlePraiseService = sysArticlePraiseService;
        this.sysArticleCommentService = sysArticleCommentService;
        this.sysArticleCategoryService = sysArticleCategoryService;
        this.sysArticleCollectionService = sysArticleCollectionService;
        this.sysArticleLabelRelationService = sysArticleLabelRelationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysArticleEntity saveOrUpdate(ArticleSro sro) {
        //检查类目是否存在
        ApiAssert.state(sysArticleCategoryService.count(Wrappers.<SysArticleCategoryEntity>lambdaQuery().eq(SysArticleCategoryEntity::getId, sro.getCategoryId())) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        SysArticleEntity entity = DozerUtils.map(sro, SysArticleEntity.class);
        this.saveOrUpdate(entity);

        //设置标签
        sysArticleLabelRelationService.saveWithArticleIdAndLabelIds(entity.getId(), sro.getLabelIds());
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        //删除文章-标签关系
        sysArticleLabelRelationService.removeById(Wrappers.<SysArticleLabelRelationEntity>lambdaQuery().eq(SysArticleLabelRelationEntity::getArticleId, id));
        //删除文章-点赞关系
        sysArticlePraiseService.removeById(Wrappers.<SysArticlePraiseEntity>lambdaQuery().eq(SysArticlePraiseEntity::getArticleId, id));
        //删除文章-评论关系
        sysArticleCommentService.removeById(Wrappers.<SysArticleCommentEntity>lambdaQuery().eq(SysArticleCommentEntity::getArticleId, id));
        //删除文章-收藏关系
        sysArticleCollectionService.removeById(Wrappers.<SysArticleCollectionEntity>lambdaQuery().eq(SysArticleCollectionEntity::getArticleId, id));
        //删除文章
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void plusReadCount(Long id, Integer increment) {
        this.getBaseMapper().plusReadCount(id, increment);
    }

    @Override
    public ArticleVo details(Long id) {
        return this.getBaseMapper().selectDetailsById(id);
    }

    @Override
    public ArticleVo details(String code) {
        return this.getBaseMapper().selectDetailsByCode(code, LocaleUtils.localeToString());
    }

    @Override
    public Page<ArticleVo> detailsPage(ArticleQro qro) {
        return this.getBaseMapper().pageVo(qro.getPage(),
                Wrappers.lambdaQuery().setEntity(DozerUtils.map(qro, SysArticleEntity.class))
        );
    }

}