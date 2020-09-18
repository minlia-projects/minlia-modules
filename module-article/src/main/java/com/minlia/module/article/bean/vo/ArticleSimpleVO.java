package com.minlia.module.article.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章简化VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSimpleVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 标题
     */
    private String title;

}
