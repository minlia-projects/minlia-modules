package com.minlia.module.article.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ArticleCategoryQro extends BaseQueryEntity {

    private Long parentId;

    private Boolean isLeaf;

    @ApiModelProperty(value = "名称", example = "首页")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "编码", example = "XXXXXX")
    @Size(max = 50)
    private String code;

    /**
     * 语言环境
     */
    private LocaleEnum locale;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

}