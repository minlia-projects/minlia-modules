package com.minlia.module.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.article.bean.SysArticleCommentQro;
import com.minlia.module.article.bean.vo.ArticleCommentVO;
import com.minlia.module.article.entity.SysArticleCommentEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文章评论 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
public interface SysArticleCommentMapper extends BaseMapper<SysArticleCommentEntity> {

    Page<ArticleCommentVO> selectDetailsPage(@Param("page") Page page, @Param("qro") SysArticleCommentQro qro);

}