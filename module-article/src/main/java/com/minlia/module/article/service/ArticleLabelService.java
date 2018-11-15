package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.bean.domain.ArticleLabel;
import com.minlia.module.article.bean.qo.ArticleLabelQO;
import com.minlia.module.article.bean.to.ArticleLabelCTO;
import com.minlia.module.article.bean.to.ArticleLabelUTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleLabelService {

    ArticleLabel create(ArticleLabelCTO cto);

    ArticleLabel update(ArticleLabelUTO uto);

    void delete(Long id);

    ArticleLabel queryById(Long id);

    long count(ArticleLabelQO qo);

    ArticleLabel one(ArticleLabelQO qo);

    List<ArticleLabel> list(ArticleLabelQO qo);

    PageInfo<ArticleLabel> page(ArticleLabelQO qo, Pageable pageable);

}
