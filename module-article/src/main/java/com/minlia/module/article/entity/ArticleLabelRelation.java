package com.minlia.module.article.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLabelRelation {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long labelId;

}