package com.minlia.module.article.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "ArticleUTO")
@Data
public class ArticleUTO implements ApiRequestBody {

    @ApiModelProperty(value = "ID", example = "1")
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "标签ID集合", example = "[1,2]")
    private List<Long> labelIds;

    @ApiModelProperty(value = "标题", example = "XXXXXX")
    @Size(max = 50)
    private String title;

    @ApiModelProperty(value = "内容", example = "XXXXXX")
    private String content;

//    @ApiModelProperty(value = "封面", example = "http://xxxxxx")
//    @URL
//    private String cover;

    @ApiModelProperty(value = "封面ETag", example = "XXXXXX")
    private String coverETag;

    @ApiModelProperty(value = "扩展字段1", example = "刺客信条")
    @Size(max = 200)
    private String attribute1;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    private Boolean enabled;

}
