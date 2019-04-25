package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AbstractEntity;
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
public class ArticlePraise extends AbstractEntity {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * true:赞、false:踩
     */
    private Boolean choose;

}

