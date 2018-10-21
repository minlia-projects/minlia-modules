package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.bean.domain.ArticleComment;
import com.minlia.module.article.bean.qo.ArticleCommentQO;
import com.minlia.module.article.bean.to.ArticleCommentCTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCommentService {

    ArticleComment create(ArticleCommentCTO cto);

    void delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQO qo);

    ArticleComment one(ArticleCommentQO qo);

    List<ArticleComment> list(ArticleCommentQO qo);

    PageInfo<ArticleComment> page(ArticleCommentQO qo, Pageable pageable);

}
