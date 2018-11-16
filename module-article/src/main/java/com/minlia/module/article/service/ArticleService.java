package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.article.bean.domain.Article;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.to.ArticleCTO;
import com.minlia.module.article.bean.to.ArticleSetLabelTO;
import com.minlia.module.article.bean.to.ArticleUTO;
import com.minlia.module.article.bean.vo.ArticleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    Article create(ArticleCTO cto);

    Article update(ArticleUTO uto);

    void delete(Long id);

    Response setLabels(ArticleSetLabelTO to);

    Article queryById(Long id);

    long count(ArticleQO qo);

    Article one(ArticleQO qo);

    List<Article> list(ArticleQO qo);

    ArticleVO oneVO(ArticleQO qo);

    List<ArticleVO> listVO(ArticleQO qo);

    PageInfo<ArticleVO> pageVO(ArticleQO qo, Pageable pageable);

}
