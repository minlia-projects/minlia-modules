package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.bean.domain.ArticleCategory;
import com.minlia.module.article.bean.qo.ArticleCategoryQO;
import com.minlia.module.article.bean.to.ArticleCategoryCTO;
import com.minlia.module.article.bean.to.ArticleCategoryUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCategoryService {

    ArticleCategory create(ArticleCategoryCTO cto);

    ArticleCategory update(ArticleCategoryUTO uto);

    void delete(Long id);

    long count(ArticleCategoryQO qo);

    ArticleCategory one(ArticleCategoryQO qo);

    List<ArticleCategory> list(ArticleCategoryQO qo);

    PageInfo<ArticleCategory> page(ArticleCategoryQO qo, Pageable pageable);

}
