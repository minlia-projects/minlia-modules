package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.article.entity.Article;
import com.minlia.module.article.ro.ArticleQRO;
import com.minlia.module.article.ro.ArticleCRO;
import com.minlia.module.article.ro.ArticleSetLabelRO;
import com.minlia.module.article.ro.ArticleURO;
import com.minlia.module.article.vo.ArticleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    Article create(ArticleCRO cto);

    Article update(ArticleURO uto);

    int delete(Long id);

    Response setLabels(ArticleSetLabelRO to);

    Article queryById(Long id);

    long count(ArticleQRO qro);

    List<Article> list(ArticleQRO qro);

    ArticleVO oneVO(ArticleQRO qro);

    List<ArticleVO> listVO(ArticleQRO qro);

    PageInfo<ArticleVO> pageVO(ArticleQRO qro, Pageable pageable);

    /**
     * 增加阅读数
     * @param id
     * @param increment
     */
    void plusReadCount(Long id, Integer increment);

}
