package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.entity.ArticleLabel;
import com.minlia.module.article.ro.ArticleLabelQRO;
import com.minlia.module.article.ro.ArticleLabelCRO;
import com.minlia.module.article.ro.ArticleLabelURO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleLabelService {

    ArticleLabel create(ArticleLabelCRO cto);

    ArticleLabel update(ArticleLabelURO uto);

    void delete(Long id);

    ArticleLabel queryById(Long id);

    long count(ArticleLabelQRO qo);

    List<ArticleLabel> list(ArticleLabelQRO qo);

    PageInfo<ArticleLabel> page(ArticleLabelQRO qo, Pageable pageable);

}
