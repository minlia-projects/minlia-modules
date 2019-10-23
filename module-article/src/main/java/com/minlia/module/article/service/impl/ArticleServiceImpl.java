package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.constant.ArticleConstants;
import com.minlia.module.article.entity.Article;
import com.minlia.module.article.entity.ArticleLabel;
import com.minlia.module.article.mapper.ArticleLabelRelationMapper;
import com.minlia.module.article.mapper.ArticleMapper;
import com.minlia.module.article.ro.*;
import com.minlia.module.article.service.ArticleCategoryService;
import com.minlia.module.article.service.ArticleLabelService;
import com.minlia.module.article.service.ArticleService;
import com.minlia.module.article.vo.ArticleVO;
import com.minlia.modules.attachment.service.AttachmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ArticleLabelService articleLabelService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleLabelRelationMapper articleLabelRelationMapper;

    @Override
    public Article create(ArticleCRO cro) {
        ApiAssert.state(articleCategoryService.count(ArticleCategoryQRO.builder().id(cro.getCategoryId()).build()) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(articleMapper.countByAll(ArticleQRO.builder().categoryId(cro.getCategoryId()).code(cro.getCode()).locale(cro.getLocale()).delFlag(false).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        Article article = mapper.map(cro, Article.class);

        //绑定附件
        if (StringUtils.isNotBlank(cro.getCover())) {
            String url = attachmentService.bindByAccessKey(cro.getCover(), article.getId().toString(), ArticleConstants.ARTICLE_COVER);
            article.setCover(url);
        }

        articleMapper.insertSelective(article);

        //设置标签
        if (CollectionUtils.isNotEmpty(cro.getLabelIds())) {
            this.setLabels(new ArticleSetLabelRO(article.getId(), cro.getLabelIds()));
        }
        return article;
    }

    @Override
    public Article update(ArticleURO uto) {
        Article article = articleMapper.selectByPrimaryKey(uto.getId());
        ApiAssert.notNull(article, SystemCode.Message.DATA_NOT_EXISTS);

        if (StringUtils.isNotBlank(uto.getCode()) && !uto.getCode().equals(article.getCode())) {
            ApiAssert.state(articleMapper.countByAll(ArticleQRO.builder().code(uto.getCode()).locale(article.getLocale()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        }

        //检查类目是否存在
        if (null != uto.getCategoryId()) {
            long count = articleCategoryService.count(ArticleCategoryQRO.builder().id(uto.getCategoryId()).build());
            ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);
        }

        //设置标签
        if (CollectionUtils.isNotEmpty(uto.getLabelIds())) {
            this.setLabels(new ArticleSetLabelRO(article.getId(), uto.getLabelIds()));
        }

        mapper.map(uto, article);

        //绑定附件
        if (StringUtils.isNotBlank(uto.getCover())) {
            String url = attachmentService.bindByAccessKey(uto.getCover(), article.getId().toString(), ArticleConstants.ARTICLE_COVER);
            article.setCover(url);
        }

        articleMapper.updateByPrimaryKeySelective(article);
        return article;
    }

    @Override
    public int delete(Long id) {
        articleLabelRelationMapper.deleteByArticleId(id);
        return articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Response setLabels(ArticleSetLabelRO to) {
        ApiAssert.notEmpty(to.getLabelIds(), SystemCode.Message.DATA_NOT_EXISTS);
        ApiAssert.state(articleMapper.countByAll(ArticleQRO.builder().id(to.getArticleId()).build()) == 1, SystemCode.Message.DATA_NOT_EXISTS);

        for (Long labelId : to.getLabelIds()) {
            ApiAssert.state(articleLabelService.count(ArticleLabelQRO.builder().id(labelId).build()) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        }

        articleLabelRelationMapper.insertBatch(to.getArticleId(), to.getLabelIds());
        return Response.success();
    }

    @Override
    public Article queryById(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public long count(ArticleQRO qro) {
        return articleMapper.countByAll(qro);
    }

    @Override
    public List<Article> list(ArticleQRO qro) {
        return articleMapper.selectByAll(qro);
    }

    @Override
    public ArticleVO oneVO(ArticleQRO qro) {
        return articleMapper.oneVO(qro);
    }

    @Override
    public List<ArticleVO> listVO(ArticleQRO qro) {
        if (StringUtils.isNotBlank(qro.getLabelCode())) {
            ArticleLabel articleLabel = articleLabelService.one(ArticleLabelQRO.builder().code(qro.getLabelCode()).locale(qro.getLocale()).build());
            if (null != articleLabel) {
                qro.setLabelId(articleLabel.getId());
            }
        }
        return articleMapper.listVO(qro);
    }

    @Override
    public PageInfo<ArticleVO> pageVO(ArticleQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> this.listVO(qro));
    }

    @Override
    public void plusReadCount(Long id, Integer increment) {
        articleMapper.plusReadCount(id, increment);
    }

}