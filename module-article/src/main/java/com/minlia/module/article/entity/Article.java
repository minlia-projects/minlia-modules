package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends AbstractEntity {

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面
     */
    private String cover;

    /**
     * 扩展字段
     */
    private String attribute1;

    /**
     * 备注
     */
    private String notes;

    /**
     * 阅读数
     */
    private Integer readCount;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
