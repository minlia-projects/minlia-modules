package com.minlia.module.article.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "ArticleCTO")
@Data
public class ArticleCTO implements ApiRequestBody {

    @ApiModelProperty(value = "类目ID", example = "1")
    @NotNull(message = "类目ID不能为空")
    private Long categoryId;

    @ApiModelProperty(value = "标题", example = "XXXXXX")
    @NotBlank(message = "标题不能为空")
    @Size(max = 50)
    private String title;

    @ApiModelProperty(value = "内容", example = "XXXXXX")
    @NotBlank(message = "内容不能为空")
    @Size(max = 5000)
    private String content;

    @ApiModelProperty(value = "封面ETag", example = "XXXXXX")
//    @NotBlank(message = "封面不能为空")
    private String coverETag;
//
//    @ApiModelProperty(value = "封面", example = "http://xxxxxx")
//    @NotBlank(message = "封面不能为空")
//    @URL
//    private String cover;

    @ApiModelProperty(value = "扩展字段1", example = "刺客信条")
    @Size(max = 200)
    private String attribute1;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 200)
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    @NotNull(message = "是否启用不能为空")
    private boolean enabled = true;

}
