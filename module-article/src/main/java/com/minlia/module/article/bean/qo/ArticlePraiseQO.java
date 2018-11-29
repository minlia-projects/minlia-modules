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
public class ArticlePraiseQO extends QueryRequest {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * true:赞、false:踩
     */
    private Boolean choose;

    private String createBy;

}