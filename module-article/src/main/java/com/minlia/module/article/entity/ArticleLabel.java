package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  文章标签
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLabel extends AbstractEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
