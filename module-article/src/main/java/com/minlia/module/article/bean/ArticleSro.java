package com.minlia.module.article.bean;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author garen
 */
@ApiModel(value = "ArticleSro")
@Data
public class ArticleSro implements ApiRequestBody {

    @ApiModelProperty(value = "ID")
    @Min(1)
    private Long id;

    @ApiModelProperty(value = "类目ID")
    @NotNull
    @Min(1)
    private Long categoryId;

    @ApiModelProperty(value = "编码")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "标题")
    @NotBlank
    @Size(max = 255)
    private String title;

    @ApiModelProperty(value = "内容")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "描述")
    @Size(max = 255)
    private String description;

    @ApiModelProperty(value = "语言环境")
    @NotNull
    private String locale;

    @ApiModelProperty(value = "草稿标识")
    @NotNull
    private Boolean draftFlag;

    @ApiModelProperty(value = "禁用标识")
    @NotNull
    private Boolean disFlag;

    @ApiModelProperty(value = "封面")
    @Size(max = 255)
    private String cover;

    @ApiModelProperty(value = "关键字")
    @Size(max = 255)
    private String keywords;

    @ApiModelProperty(value = "备注")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "扩展字段1")
    @Size(max = 255)
    private String attribute1;

    @ApiModelProperty(value = "扩展字段2")
    @Size(max = 255)
    private String attribute2;

    @ApiModelProperty(value = "扩展字段3")
    @Size(max = 255)
    private String attribute3;

    @ApiModelProperty(value = "扩展字段4")
    @Size(max = 255)
    private String attribute4;

    @ApiModelProperty(value = "扩展字段5")
    @Size(max = 255)
    private String attribute5;


    @ApiModelProperty(value = "标签ID集合")
    private Set<Long> labelIds;

}