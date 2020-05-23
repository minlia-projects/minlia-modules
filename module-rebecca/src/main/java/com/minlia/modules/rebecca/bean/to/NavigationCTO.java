package com.minlia.modules.rebecca.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "导航-创建")
public class NavigationCTO implements ApiRequestBody {

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路由")
    private String state;

    @ApiModelProperty(value = "顺序")
    private Integer orders;

}
