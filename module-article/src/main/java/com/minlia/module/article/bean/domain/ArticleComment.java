package com.minlia.module.article.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
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
public class ArticleComment extends AbstractEntity {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 内容
     */
    private String content;

}
