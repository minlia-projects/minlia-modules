package com.minlia.module.article.entity;

import com.minlia.module.article.vo.ArticleSimpleVO;
import com.minlia.module.data.entity.AbstractEntity;
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

    private Long id;

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
    private String remark;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

    private Boolean delFlag;

    /**
     * 文章子项
     */
    List<ArticleSimpleVO> articles;

}