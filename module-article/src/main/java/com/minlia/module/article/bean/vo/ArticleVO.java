package com.minlia.module.article.bean.vo;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 标签ID集
     */
    private List<Long> labelIds;

    /**
     * 标签名称集
     */
    private List<String> labelNames;

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

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 阅读数
     */
    private Integer readCount;

}
