package com.minlia.module.article.mapper;

import com.minlia.module.article.ro.ArticleCollectionQRO;
import com.minlia.module.article.vo.ArticleVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleCollectionMapper {

    int create(Long articleId, String guid);

    int delete(Long articleId, String guid);

    long queryCount(ArticleCollectionQRO qro);

    List<ArticleVO> queryList(ArticleCollectionQRO qro);

}
