package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.ArticlePraise;
import com.minlia.module.article.bean.qo.ArticlePraiseQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticlePraiseMapper {

    long create(ArticlePraise articlePraise);

    long update(ArticlePraise articlePraise);

    long delete(Long id);

    ArticlePraise queryById(Long id);

    long count(ArticlePraiseQO qo);

    ArticlePraise one(ArticlePraiseQO qo);

    List<ArticlePraise> list(ArticlePraiseQO qo);

}
