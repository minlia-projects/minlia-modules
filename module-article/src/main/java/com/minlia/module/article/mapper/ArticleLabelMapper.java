package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleLabel;
import com.minlia.module.article.ro.ArticleLabelQRO;

import java.util.List;

public interface ArticleLabelMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ArticleLabel record);

    ArticleLabel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLabel record);

    long countByAll(ArticleLabelQRO qro);

    List<ArticleLabel> selectByAll(ArticleLabelQRO qro);

}