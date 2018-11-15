package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.ArticleLabel;
import com.minlia.module.article.bean.qo.ArticleLabelQO;

import java.util.List;

/**
 *
 * Created by garen on 2018/10/16.
 */
public interface ArticleLabelMapper {

    long create(ArticleLabel articleLabel);

    long update(ArticleLabel articleLabel);

    long delete(Long id);

    long count(ArticleLabelQO qo);

    ArticleLabel one(ArticleLabelQO qo);

    List<ArticleLabel> list(ArticleLabelQO qo);

}
