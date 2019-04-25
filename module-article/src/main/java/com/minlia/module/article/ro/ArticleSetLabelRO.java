package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
@AllArgsConstructor
public class ArticleSetLabelRO implements ApiRequestBody {

    @ApiModelProperty(value = "文章ID", example = "1")
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    @ApiModelProperty(value = "标签集", example = "[1,2,3]")
    @NotNull(message = "标签集不能为空")
    private List<Long> labelIds;

}
