package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.ro.ArticleCategoryCRO;
import com.minlia.module.article.ro.ArticleCategoryURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCategoryService {

    ArticleCategory create(ArticleCategoryCRO cto);

    ArticleCategory update(ArticleCategoryURO uto);

    void delete(Long id);

    long count(ArticleCategoryQRO qo);

    ArticleCategory one(ArticleCategoryQRO qo);

    List<ArticleCategory> list(ArticleCategoryQRO qo);

    PageInfo<ArticleCategory> page(ArticleCategoryQRO qo, Pageable pageable);

}
