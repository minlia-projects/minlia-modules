package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.qo.ArticleCollectionQO;
import com.minlia.module.article.bean.vo.ArticleVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleCollectionMapper {

    int create(Long articleId, String guid);

    int delete(Long articleId, String guid);

    long queryCount(ArticleCollectionQO qo);

    List<ArticleVO> queryList(ArticleCollectionQO qo);

}
