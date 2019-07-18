package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleCategoryCRO;
import com.minlia.module.article.ro.ArticleCategoryURO;
import com.minlia.module.article.mapper.ArticleCategoryMapper;
import com.minlia.module.article.service.ArticleCategoryService;
import com.minlia.module.article.service.ArticleService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public ArticleCategory create(ArticleCategoryCRO cto) {
        ApiAssert.state(articleCategoryMapper.count(ArticleCategoryQRO.builder().code(cto.getCode()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        ArticleCategory articleCategory = mapper.map(cto, ArticleCategory.class);
        articleCategoryMapper.create(articleCategory);
        return articleCategory;
    }

    @Override
    public ArticleCategory update(ArticleCategoryURO uto) {
        ArticleCategory articleCategory = articleCategoryMapper.one(ArticleCategoryQRO.builder().id(uto.getId()).build());
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, articleCategory);
        articleCategoryMapper.update(articleCategory);
        return articleCategory;
    }

    @Override
    public void delete(Long id) {
        ArticleCategory articleCategory = articleCategoryMapper.one(ArticleCategoryQRO.builder().id(id).build());
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);

        //判断是否有子项
        long count = articleService.count(ArticleQRO.builder().categoryId(id).build());
        ApiAssert.state(count == 0, "存在广告无法删除");
        articleCategoryMapper.delete(articleCategory.getId());
    }

    @Override
    public long count(ArticleCategoryQRO qro) {
        return articleCategoryMapper.count(qro);
    }

    @Override
    public ArticleCategory one(ArticleCategoryQRO qro) {
        ArticleCategory articleCategory = articleCategoryMapper.one(qro);
        return articleCategory;
    }

    @Override
    public List<ArticleCategory> list(ArticleCategoryQRO qro) {
        List<ArticleCategory> list = articleCategoryMapper.list(qro);
        return list;
    }

    @Override
    public PageInfo<ArticleCategory> page(ArticleCategoryQRO qro, Pageable pageable) {
        PageInfo<ArticleCategory> pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> articleCategoryMapper.list(qro));
        return pageInfo;
    }

//    private void setArticles(ArticleCategory articleCategory) {
//        if (null != articleCategory) {
//            articleCategory.setArticles(articleService.list(ArticleQRO.builder().categoryId(articleCategory.getId()).build()));
//        }
//    }
//
//    private void setArticles(List<ArticleCategory> articleCategories) {
//        for (ArticleCategory articleCategory : articleCategories) {
//            articleCategory.setArticles(articleService.list(ArticleQRO.builder().categoryId(articleCategory.getId()).build()));
//        }
//    }

}
