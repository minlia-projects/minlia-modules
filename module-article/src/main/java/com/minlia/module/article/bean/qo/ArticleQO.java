package com.minlia.module.article.bean.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQO {

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 标签ID
     */
    private Long labelId;

    /**
     * 文章ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 是否启用
     */
    private Boolean enabled;

}