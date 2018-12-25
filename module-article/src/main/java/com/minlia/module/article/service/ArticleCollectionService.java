package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.bean.qo.ArticleCollectionQO;
import com.minlia.module.article.bean.vo.ArticleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCollectionService {

    boolean collection(Long articleId);

    boolean isCollected(Long articleId);

    long queryCount(ArticleCollectionQO qo);

    List<ArticleVO> queryList(ArticleCollectionQO qo);

    PageInfo<ArticleVO> queryPage(ArticleCollectionQO qo, Pageable pageable);

}
