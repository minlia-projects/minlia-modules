package com.minlia.module.article.mapper;

import com.minlia.module.article.bean.domain.ArticleComment;
import com.minlia.module.article.bean.qo.ArticleCommentQO;
import com.minlia.module.article.bean.vo.ArticleCommentVO;
import com.minlia.module.article.bean.vo.ArticleMyCommentVO;

import java.util.List;

/**
 * Created by garen on 2018/10/16.
 */
public interface ArticleCommentMapper {

    long create(ArticleComment articleComment);

    long update(ArticleComment articleComment);

    long delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQO qo);

    List<ArticleComment> list(ArticleCommentQO qo);


    /**
     * 查询评论集合
     * @param qo
     * @return
     */
    List<ArticleCommentVO> queryDetailsList(ArticleCommentQO qo);

    /**
     * 查询我的评论集合
     * @param qo
     * @return
     */
    List<ArticleMyCommentVO> queryMyList(ArticleCommentQO qo);

}
