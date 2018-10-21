package com.minlia.module.article.bean.domain;

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
     * 关键词
     */
    private String keywords;

    /**
     * 备注
     */
    private String notes;

    /**
     * 扩展字段1：所属游戏
     */
    private String attribute1;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
