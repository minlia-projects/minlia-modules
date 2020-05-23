package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@Data
public class ArticleCommentCRO implements ApiRequestBody {

    @ApiModelProperty(value = "文章ID", example = "1")
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    @ApiModelProperty(value = "内容", example = "XXXXXX")
    @NotBlank(message = "内容不能为空")
    @Size(max = 300)
    private String content;

}
