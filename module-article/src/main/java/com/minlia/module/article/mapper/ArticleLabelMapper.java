package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleLabel;
import com.minlia.module.article.ro.ArticleLabelQRO;

import java.util.List;

/**
 *
 * Created by garen on 2018/10/16.
 */
public interface ArticleLabelMapper {

    long create(ArticleLabel articleLabel);

    long update(ArticleLabel articleLabel);

    long delete(Long id);

    long count(ArticleLabelQRO qo);

    ArticleLabel one(ArticleLabelQRO qo);

    List<ArticleLabel> list(ArticleLabelQRO qo);

}
