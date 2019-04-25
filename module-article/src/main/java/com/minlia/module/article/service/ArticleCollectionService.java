package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.ro.ArticleCollectionQRO;
import com.minlia.module.article.vo.ArticleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCollectionService {

    boolean collection(Long articleId);

    boolean isCollected(Long articleId);

    long queryCount(ArticleCollectionQRO qro);

    List<ArticleVO> queryList(ArticleCollectionQRO qro);

    PageInfo<ArticleVO> queryPage(ArticleCollectionQRO qro, Pageable pageable);

}
