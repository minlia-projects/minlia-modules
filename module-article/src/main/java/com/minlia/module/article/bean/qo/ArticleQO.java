package com.minlia.module.article.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQO extends QueryRequest {

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
     * 扩展字段
     */
    private String attribute1;

    /**
     * 是否启用
     */
    private Boolean enabled;

}