package com.minlia.module.article.bean;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel
@Data
public class ArticleLabelSro implements ApiRequestBody {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "编码", example = "HOME")
    @NotBlank(message = "编码不能为空")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "语言环境")
    @NotNull
    private LocaleEnum locale;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    @NotNull(message = "禁用标识不能为空")
    private Boolean disFlag;

}