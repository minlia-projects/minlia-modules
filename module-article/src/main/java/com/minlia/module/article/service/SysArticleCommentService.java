package com.minlia.module.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.article.bean.ArticleCommentCro;
import com.minlia.module.article.bean.SysArticleCommentQro;
import com.minlia.module.article.bean.vo.ArticleCommentVO;
import com.minlia.module.article.entity.SysArticleCommentEntity;
import com.minlia.module.data.entity.BaseQueryEntity;

/**
 * <p>
 * 文章评论 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleCommentService extends IService<SysArticleCommentEntity> {

    /**
     * 添加评论
     *
     * @param cro
     * @return
     */
    SysArticleCommentEntity create(ArticleCommentCro cro);

    /**
     * 是否评论
     *
     * @param articleId
     * @return
     */
    boolean isCommented(Long articleId);

    /**
     * 我的评论/分页
     *
     * @return
     */
    Page myPage(BaseQueryEntity qro);

    Page<ArticleCommentVO> detailsPage(SysArticleCommentQro qro);

}