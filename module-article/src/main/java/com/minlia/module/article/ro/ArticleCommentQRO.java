package com.minlia.module.article.ro;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentQRO extends QueryRequest {

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