package com.minlia.module.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.bean.domain.ArticleCategory;
import com.minlia.module.article.bean.qo.ArticleCategoryQO;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.to.ArticleCategoryCTO;
import com.minlia.module.article.bean.to.ArticleCategoryUTO;
import com.minlia.module.article.mapper.ArticleCategoryMapper;
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
    public ArticleCategory create(ArticleCategoryCTO cto) {
        ApiAssert.state(articleCategoryMapper.count(ArticleCategoryQO.builder().code(cto.getCode()).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        ArticleCategory articleCategory = mapper.map(cto, ArticleCategory.class);
        articleCategoryMapper.create(articleCategory);
        return articleCategory;
    }

    @Override
    public ArticleCategory update(ArticleCategoryUTO uto) {
        ArticleCategory articleCategory = articleCategoryMapper.queryById(uto.getId());
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, articleCategory);
        articleCategoryMapper.update(articleCategory);
        return articleCategory;
    }

    @Override
    public void delete(Long id) {
        ArticleCategory articleCategory = articleCategoryMapper.queryById(id);
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);

        //判断是否有子项
        long count = articleService.count(ArticleQO.builder().categoryId(id).build());
        ApiAssert.state(count == 0, "存在广告无法删除");
        articleCategoryMapper.delete(articleCategory.getId());
    }

    @Override
    public long count(ArticleCategoryQO qo) {
        return articleCategoryMapper.count(qo);
    }

    @Override
    public ArticleCategory one(ArticleCategoryQO qo) {
        ArticleCategory articleCategory = articleCategoryMapper.one(qo);
        this.setArticles(articleCategory);
        return articleCategory;
    }

    @Override
    public List<ArticleCategory> list(ArticleCategoryQO qo) {
        List<ArticleCategory> list = articleCategoryMapper.list(qo);
        this.setArticles(list);
        return list;
    }

    @Override
    public PageInfo<ArticleCategory> page(ArticleCategoryQO qo, Pageable pageable) {
        PageInfo<ArticleCategory> pageInfo = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> articleCategoryMapper.list(qo));
        this.setArticles(pageInfo.getList());
        return pageInfo;
    }

    private void setArticles(ArticleCategory articleCategory) {
        if (null != articleCategory) {
            articleCategory.setArticles(articleService.list(ArticleQO.builder().categoryId(articleCategory.getId()).build()));
        }
    }

    private void setArticles(List<ArticleCategory> articleCategories) {
        for (ArticleCategory articleCategory : articleCategories) {
            articleCategory.setArticles(articleService.list(ArticleQO.builder().categoryId(articleCategory.getId()).build()));
        }
    }

}
