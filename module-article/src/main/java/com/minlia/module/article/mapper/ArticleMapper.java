package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.Article;
import com.minlia.module.article.bean.qo.ArticleQO;
import com.minlia.module.article.bean.to.ArticleSetLabelTO;
import com.minlia.module.article.bean.vo.ArticleVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleMapper {

    long create(Article article);

    long update(Article article);

    long delete(Long id);

    void setLabels(ArticleSetLabelTO to);

    Article queryById(Long id);

    long count(ArticleQO qo);

    Article one(ArticleQO qo);

    List<Article> list(ArticleQO qo);

    ArticleVO oneVO(ArticleQO qo);

    List<ArticleVO> listVO(ArticleQO qo);

    long plusReadCount(Long id, Integer increment);

}