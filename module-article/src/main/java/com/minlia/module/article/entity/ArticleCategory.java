package com.minlia.module.article.entity;

import com.minlia.module.article.vo.ArticleSimpleVO;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import lombok.*;

import java.util.List;

/**
 * 文章类目
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategory extends AbstractEntity {

    /**
     * ID
     */
    private Long id;

    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

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

    /**
     * 删除标识
     */
    private Boolean delFlag;

    /**
     * 文章子项
     */
    List<ArticleSimpleVO> articles;

    List<ArticleCategory> children;

}