package com.minlia.module.article.ro;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    /**
     * 类目ID
     */
    private Long categoryId;
    private String categoryCode;

    private Long labelId;
    private String labelCode;

    @Size(max = 64)
    private String code;

    /**
     * 标题
     */
    @Size(max = 255)
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 描述
     */
    @Size(max = 255)
    private String description;

    /**
     * 语言环境
     */
    @NotNull
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
    @Size(max = 255)
    private String cover;

    /**
     * 关键字
     */
    @Size(max = 255)
    private String keywords;

    /**
     * 备注
     */
    @Size(max = 255)
    private String remark;

    /**
     * 扩展字段1
     */
    @Size(max = 255)
    private String attribute1;

    /**
     * 扩展字段2
     */
    @Size(max = 255)
    private String attribute2;

    /**
     * 扩展字段3
     */
    @Size(max = 255)
    private String attribute3;

    /**
     * 扩展字段4
     */
    @Size(max = 255)
    private String attribute4;

    /**
     * 扩展字段5
     */
    @Size(max = 255)
    private String attribute5;

}