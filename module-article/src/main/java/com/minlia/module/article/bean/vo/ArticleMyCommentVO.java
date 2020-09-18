package com.minlia.module.article.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章评论
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleMyCommentVO {

    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章封面
     */
    private String articleCover;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章是否启用
     */
    private Boolean articleEnabled;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime createDate;

}
