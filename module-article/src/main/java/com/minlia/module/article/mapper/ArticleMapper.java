package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.Article;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleSetLabelRO;
import com.minlia.module.article.vo.ArticleVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleMapper {

    long create(Article article);

    long update(Article article);

    long delete(Long id);

    void setLabels(ArticleSetLabelRO to);

    Article queryById(Long id);

    long count(ArticleQRO qro);

    Article one(ArticleQRO qro);

    List<Article> list(ArticleQRO qro);

    ArticleVO oneVO(ArticleQRO qro);

    List<ArticleVO> listVO(ArticleQRO qro);

    long plusReadCount(Long id, Integer increment);

}
