package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class ArticlePraiseRO implements ApiRequestBody {

    @ApiModelProperty(value = "文章ID", example = "1")
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    @ApiModelProperty(value = "选择", example = "XXXXXX")
    @NotNull(message = "选择不能为空")
    private Boolean choose;

}
