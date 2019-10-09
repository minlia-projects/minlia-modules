package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.mapper.ArticleCollectionMapper;
import com.minlia.module.article.ro.ArticleCollectionQRO;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.service.ArticleCollectionService;
import com.minlia.module.article.service.ArticleService;
import com.minlia.module.article.vo.ArticleVO;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCollectionServiceImpl implements ArticleCollectionService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCollectionMapper articleCollectionMapper;

    @Override
    public boolean collection(Long articleId) {
        long count = articleService.count(ArticleQRO.builder().id(articleId).enabled(true).build());
        ApiAssert.state(count > 0, SystemCode.Message.DATA_NOT_EXISTS);

        if (this.isCollected(articleId)) {
            articleCollectionMapper.delete(articleId, SecurityContextHolder.getCurrentGuid());
            return false;
        } else {
            articleCollectionMapper.create(articleId, SecurityContextHolder.getCurrentGuid());
            return true;
        }
    }

    @Override
    public boolean isCollected(Long articleId) {
        long collectionCount = this.queryCount(ArticleCollectionQRO.builder().articleId(articleId).collector(SecurityContextHolder.getCurrentGuid()).build());
        return collectionCount > 0;
    }

    @Override
    public long queryCount(ArticleCollectionQRO qro) {
        return articleCollectionMapper.queryCount(qro);
    }

    @Override
    public List<ArticleVO> queryList(ArticleCollectionQRO qro) {
        return articleCollectionMapper.queryList(qro);
    }

    @Override
    public PageInfo<ArticleVO> queryPage(ArticleCollectionQRO qro, Pageable pageable) {
        PageInfo<ArticleVO> pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> articleCollectionMapper.queryList(qro));
        return pageInfo;
    }

}
