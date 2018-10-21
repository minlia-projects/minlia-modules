package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.ArticleCategory;
import com.minlia.module.article.bean.qo.ArticleCategoryQO;

import java.util.List;

/**
 *
 * Created by garen on 2018/10/16.
 */
public interface ArticleCategoryMapper {

    long create(ArticleCategory articleCategory);

    long update(ArticleCategory articleCategory);

    long delete(Long id);

    ArticleCategory queryById(Long id);

    long count(ArticleCategoryQO qo);

    ArticleCategory one(ArticleCategoryQO qo);

    List<ArticleCategory> list(ArticleCategoryQO qo);

}
