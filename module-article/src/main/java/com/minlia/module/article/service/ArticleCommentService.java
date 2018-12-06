package com.minlia.module.article.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.article.bean.domain.ArticleComment;
import com.minlia.module.article.bean.qo.ArticleCommentQO;
import com.minlia.module.article.bean.to.ArticleCommentCTO;
import com.minlia.module.article.bean.vo.ArticleCommentVO;
import com.minlia.module.article.bean.vo.ArticleMyCommentVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleCommentService {

    ArticleComment create(ArticleCommentCTO cto);

    void delete(Long id);

    ArticleComment queryById(Long id);

    long count(ArticleCommentQO qo);

    List<ArticleComment> list(ArticleCommentQO qo);

    PageInfo<ArticleComment> page(ArticleCommentQO qo, Pageable pageable);


    /**
     * 查询评论集合
     * @param qo
     * @return
     */
    List<ArticleCommentVO> queryDetailsList(ArticleCommentQO qo);

    /**
     * 查询评论分页
     * @param qo
     * @return
     */
    PageInfo<ArticleCommentVO> queryDetailsPage(ArticleCommentQO qo, Pageable pageable);

    /**
     * 查询我的评论集合
     * @param qo
     * @return
     */
    List<ArticleMyCommentVO> queryMyList(ArticleCommentQO qo);

    /**
     * 查询我的评论分页
     * @param qo
     * @return
     */
    PageInfo<ArticleMyCommentVO> queryMyPage(ArticleCommentQO qo, Pageable pageable);

}
