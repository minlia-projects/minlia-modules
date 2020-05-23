package com.minlia.modules.rebecca.menu.bean;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "菜单-分配")
public class MenuGrantRO implements ApiRequestBody {

    @ApiModelProperty(value = "角色ID")
    @NotNull
    private Long roleId;

    @ApiModelProperty(value = "导航ID集合")
    @NotNull
    private List<Long> ids;

}
