package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.article.bean.domain.ArticlePraise;
import com.minlia.module.article.bean.qo.ArticlePraiseQO;
import com.minlia.module.article.bean.to.ArticlePraiseTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticlePraiseService {

    Response operate(ArticlePraiseTO to);

    Response trample(Long articleId);

    void delete(Long id);

    ArticlePraise queryById(Long id);

    long count(ArticlePraiseQO qo);

    ArticlePraise one(ArticlePraiseQO qo);

    List<ArticlePraise> list(ArticlePraiseQO qo);

    PageInfo<ArticlePraise> page(ArticlePraiseQO qo, Pageable pageable);

}
