package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.ro.ArticleCategoryQRO;

import java.util.List;

/**
 *
 * Created by garen on 2018/10/16.
 */
public interface ArticleCategoryMapper {

    long create(ArticleCategory articleCategory);

    long update(ArticleCategory articleCategory);

    long delete(Long id);

    long count(ArticleCategoryQRO qo);

    ArticleCategory one(ArticleCategoryQRO qo);

    List<ArticleCategory> list(ArticleCategoryQRO qo);

}
