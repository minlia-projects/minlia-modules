package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.ArticleComment;
import com.minlia.module.article.bean.qo.ArticleCommentQO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleCommentMapper {

    long create(ArticleComment articleComment);

    long update(ArticleComment articleComment);

    long delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQO qo);

    ArticleComment one(ArticleCommentQO qo);

    List<ArticleComment> list(ArticleCommentQO qo);

}
