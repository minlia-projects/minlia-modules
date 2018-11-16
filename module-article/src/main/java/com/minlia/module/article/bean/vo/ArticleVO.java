package com.minlia.module.article.bean.vo;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO extends AbstractEntity {

    /**
     * 类目ID
     */
    private String categoryName;

    /**
     * 标签
     */
    private String labels;

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
     * 扩展字段1：所属游戏
     */
    private String attribute1;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
