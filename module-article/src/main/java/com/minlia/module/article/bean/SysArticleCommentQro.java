package com.minlia.module.article.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author garen
 */
@Data
public class SysArticleCommentQro extends QueryRequest {

    /**
     * 文章ID
     */
    @NotNull
    private Long articleId;

    /**
     * 创建人
     */
    private String operator;

}