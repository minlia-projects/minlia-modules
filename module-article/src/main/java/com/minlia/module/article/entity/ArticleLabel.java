package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AuditableEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLabel extends AuditableEntity {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 语言环境
     */
    private LocaleEnum locale;

    /**
     * 备注
     */
    private String remark;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

}
