package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticlePraise;
import com.minlia.module.article.ro.ArticlePraiseQRO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticlePraiseMapper {

    long create(ArticlePraise articlePraise);

    long update(ArticlePraise articlePraise);

    long delete(Long id);

    ArticlePraise queryById(Long id);

    long count(ArticlePraiseQRO qo);

    ArticlePraise one(ArticlePraiseQRO qo);

    List<ArticlePraise> list(ArticlePraiseQRO qo);

}
