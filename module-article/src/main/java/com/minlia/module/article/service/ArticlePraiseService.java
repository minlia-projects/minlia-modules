package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.article.entity.ArticlePraise;
import com.minlia.module.article.ro.ArticlePraiseQRO;
import com.minlia.module.article.ro.ArticlePraiseRO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticlePraiseService {

    Response operate(ArticlePraiseRO to);

    Response trample(Long articleId);

    void delete(Long id);

    ArticlePraise queryById(Long id);

    long count(ArticlePraiseQRO qo);

    ArticlePraise one(ArticlePraiseQRO qo);

    List<ArticlePraise> list(ArticlePraiseQRO qo);

    PageInfo<ArticlePraise> page(ArticlePraiseQRO qo, Pageable pageable);

}
