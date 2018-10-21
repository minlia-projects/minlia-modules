package com.minlia.module.article.bean.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePraiseQO {

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