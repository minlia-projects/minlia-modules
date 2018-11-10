package com.minlia.module.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.domain.Article;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.qo.ArticleCategoryQO;
import com.minlia.module.article.bean.to.ArticleCTO;
import com.minlia.module.article.bean.to.ArticleUTO;
import com.minlia.module.article.mapper.ArticleMapper;
import com.minlia.modules.attachment.constant.AttachmentCode;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.service.AttachmentService;
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
    private ArticleCategoryService articleCategoryService;

    @Override
    public Article create(ArticleCTO cto) {
        long count = articleCategoryService.count(ArticleCategoryQO.builder().id(cto.getCategoryId()).build());
        ApiAssert.state(count == 1, SystemCode.Message.DATA_NOT_EXISTS);

        ApiAssert.state(articleMapper.count(ArticleQO.builder().categoryId(cto.getCategoryId()).title(cto.getTitle()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);

        if (StringUtils.isNotBlank(cto.getCoverETag())) {
            Attachment attachment = attachmentService.queryByKey(cto.getCoverETag());
            ApiAssert.notNull(attachment, AttachmentCode.Message.ETAG_NOT_EXISTS);
        }

        Article article = mapper.map(cto, Article.class);
        articleMapper.create(article);

        //绑定附件
        if (StringUtils.isNotBlank(cto.getCoverETag())) {
            attachmentService.bindByAccessKey(cto.getCoverETag(), article.getId().toString(), "ARTICLE_COVER");
        }
        return article;
    }

    @Override
    public Article update(ArticleUTO uto) {
        Article article = articleMapper.queryById(uto.getId());
        ApiAssert.notNull(article, SystemCode.Message.DATA_NOT_EXISTS);

        //绑定附件
        if (StringUtils.isNotBlank(uto.getCoverETag())) {
            attachmentService.bindByAccessKey(uto.getCoverETag(), article.getId().toString(), "ARTICLE_COVER");
        }

        mapper.map(uto, article);
        articleMapper.update(article);
        return article;
    }

    @Override
    public void delete(Long id) {
        Article article = articleMapper.queryById(id);
        ApiAssert.notNull(article, SystemCode.Message.DATA_NOT_EXISTS);
        articleMapper.delete(id);
    }

    @Override
    public Article queryById(Long id) {
        return articleMapper.queryById(id);
    }

    @Override
    public long count(ArticleQO qo) {
        return articleMapper.count(qo);
    }

    @Override
    public Article one(ArticleQO qo) {
        return articleMapper.one(qo);
    }

    @Override
    public List<Article> list(ArticleQO qo) {
        return articleMapper.list(qo);
    }

    @Override
    public PageInfo<Article> page(ArticleQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> articleMapper.list(qo));
    }

}