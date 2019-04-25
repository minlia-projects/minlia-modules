package com.minlia.module.article.ro;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@Data
public class ArticleLabelCRO implements ApiRequestBody {

    @ApiModelProperty(value = "名称", example = "首页")
    @NotBlank(message = "名称不能为空")
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "备注", example = "XXXXXX")
    @Size(max = 200)
    private String notes;

    @ApiModelProperty(value = "是否启用", example = "true")
    @NotNull(message = "是否启用不能为空")
    private boolean enabled = true;

}
