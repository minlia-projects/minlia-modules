package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.entity.ArticleComment;
import com.minlia.module.article.ro.ArticleCommentQRO;
import com.minlia.module.article.ro.ArticleCommentCRO;
import com.minlia.module.article.vo.ArticleCommentVO;
import com.minlia.module.article.vo.ArticleMyCommentVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCommentService {

    ArticleComment create(ArticleCommentCRO cto);

    void delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQRO qo);

    List<ArticleComment> list(ArticleCommentQRO qo);

    PageInfo<ArticleComment> page(ArticleCommentQRO qo, Pageable pageable);


    /**
     * 查询评论集合
     * @param qo
     * @return
     */
    List<ArticleCommentVO> queryDetailsList(ArticleCommentQRO qo);

    /**
     * 查询评论分页
     * @param qo
     * @return
     */
    PageInfo<ArticleCommentVO> queryDetailsPage(ArticleCommentQRO qo, Pageable pageable);

    /**
     * 查询我的评论集合
     * @param qo
     * @return
     */
    List<ArticleMyCommentVO> queryMyList(ArticleCommentQRO qo);

    /**
     * 查询我的评论分页
     * @param qo
     * @return
     */
    PageInfo<ArticleMyCommentVO> queryMyPage(ArticleCommentQRO qo, Pageable pageable);

}
