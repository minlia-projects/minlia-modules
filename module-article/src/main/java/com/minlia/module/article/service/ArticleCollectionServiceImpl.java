package com.minlia.module.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.qo.ArticleCollectionQO;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.vo.ArticleVO;
import com.minlia.module.article.mapper.ArticleCollectionMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCollectionServiceImpl implements ArticleCollectionService{

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCollectionMapper articleCollectionMapper;

    @Override
    public boolean collection(Long articleId) {
        long count = articleService.count(ArticleQO.builder().id(articleId).enabled(true).build());
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
        long collectionCount = this.queryCount(ArticleCollectionQO.builder().articleId(articleId).collector(SecurityContextHolder.getCurrentGuid()).build());
        return collectionCount > 0;
    }

    @Override
    public long queryCount(ArticleCollectionQO qo) {
        return articleCollectionMapper.queryCount(qo);
    }

    @Override
    public List<ArticleVO> queryList(ArticleCollectionQO qo) {
        return articleCollectionMapper.queryList(qo);
    }

    @Override
    public PageInfo<ArticleVO> queryPage(ArticleCollectionQO qo, Pageable pageable) {
        PageInfo<ArticleVO> pageInfo = PageHelper.startPage(qo.getPageNumber(), qo.getPageSize()).doSelectPageInfo(()-> articleCollectionMapper.queryList(qo));
        return pageInfo;
    }

}
