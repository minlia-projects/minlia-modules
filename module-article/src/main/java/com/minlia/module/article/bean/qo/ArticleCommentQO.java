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
public class ArticleCommentQO extends QueryRequest {

    /**
     * ID
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 创建人
     */
    private String createBy;

}