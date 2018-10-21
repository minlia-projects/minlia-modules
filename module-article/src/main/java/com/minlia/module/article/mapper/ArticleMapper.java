package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.Article;
import com.minlia.module.article.bean.qo.ArticleQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleMapper {

    long create(Article article);

    long update(Article article);

    long delete(Long id);

    Article queryById(Long id);

    long count(ArticleQO qo);

    Article one(ArticleQO qo);

    List<Article> list(ArticleQO qo);

}
