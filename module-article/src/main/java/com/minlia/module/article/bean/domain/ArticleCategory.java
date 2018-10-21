package com.minlia.module.article.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;

import java.util.List;

/**
 *  文章类目
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategory extends AbstractEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 文章子项
     */
    List<Article> articles;

}
