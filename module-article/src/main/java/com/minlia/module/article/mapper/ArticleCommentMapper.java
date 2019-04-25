package com.minlia.module.article.mapper;

import com.minlia.module.article.entity.ArticleComment;
import com.minlia.module.article.ro.ArticleCommentQRO;
import com.minlia.module.article.vo.ArticleCommentVO;
import com.minlia.module.article.vo.ArticleMyCommentVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleCommentMapper {

    long create(ArticleComment articleComment);

    long update(ArticleComment articleComment);

    long delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQRO qro);

    List<ArticleComment> list(ArticleCommentQRO qro);


    /**
     * 查询评论集合
     * @param qro
     * @return
     */
    List<ArticleCommentVO> queryDetailsList(ArticleCommentQRO qro);

    /**
     * 查询我的评论集合
     * @param qro
     * @return
     */
    List<ArticleMyCommentVO> queryMyList(ArticleCommentQRO qro);

}
