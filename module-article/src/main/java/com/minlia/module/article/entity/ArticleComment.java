package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章评论
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleComment extends AuditableEntity {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 内容
     */
    private String content;

}
