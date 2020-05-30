package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章点赞
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePraise extends AuditableEntity {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * true:赞、false:踩
     */
    private Boolean choose;

}

