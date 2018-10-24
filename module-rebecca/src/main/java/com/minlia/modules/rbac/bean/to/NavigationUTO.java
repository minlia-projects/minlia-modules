package com.minlia.modules.rbac.bean.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "导航-更新")
public class NavigationUTO extends NavigationCTO {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "展示/隐藏")
    private Boolean display;

}
