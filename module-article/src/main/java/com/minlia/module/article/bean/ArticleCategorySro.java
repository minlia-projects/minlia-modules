package com.minlia.module.article.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel
@Data
public class ArticleCategorySro implements ApiRequestBody {

    private Long id;

    @NotNull
    @Min(0)
    private Long parentId;

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "编码", example = "XXXXXX")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String code;

    /**
     * 语言环境
     */
    @NotNull
    private LocaleEnum locale;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    @NotNull(message = "是否启用不能为空")
    private Boolean disFlag;

}