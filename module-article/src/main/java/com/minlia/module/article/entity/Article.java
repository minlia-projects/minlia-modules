package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.modules.attachment.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

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
     * 描述
     */
    private String description;

    /**
     * 语言环境
     */
    private LocaleEnum locale;

    /**
     * 草稿标识
     */
    private Boolean draftFlag;

    /**
     * 禁用标识
     */
    private Boolean disFlag;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    /**
     * 阅读数
     */
    private Long readCount;

    /**
     * 封面
     */
    private String cover;
    @Transient
    private Attachment coverObj;


    /**
     * 关键字
     */
    private String keywords;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展字段1
     */
    private String attribute1;

    /**
     * 扩展字段2
     */
    private String attribute2;

    /**
     * 扩展字段3
     */
    private String attribute3;

    /**
     * 扩展字段4
     */
    private String attribute4;

    /**
     * 扩展字段5
     */
    private String attribute5;

}