package com.minlia.module.article.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author garen
 */
@Data
public class ArticleQro extends BaseQueryEntity {

    @ApiModelProperty(value = "标签ID集合")
    private List<Long> labelIds;


    @ApiModelProperty(value = "类目ID")
    private Long categoryId;

    private String categoryCode;

    @ApiModelProperty(value = "编码")
    @Size(max = 64)
    private String code;

    @ApiModelProperty(value = "标题")
    @Size(max = 255)
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "描述")
    @Size(max = 255)
    private String description;

    @ApiModelProperty(value = "语言环境")
    @NotNull
    private String locale;

    @ApiModelProperty(value = "草稿标识")
    private Boolean draftFlag;

    @ApiModelProperty(value = "禁用标识")
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

}