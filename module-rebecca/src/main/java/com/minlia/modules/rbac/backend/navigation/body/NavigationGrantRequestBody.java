package com.minlia.modules.rbac.backend.navigation.body;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "导航-分配")
public class NavigationGrantRequestBody implements ApiRequestBody {

    @ApiModelProperty(value = "角色ID")
    @NotNull
    private Long roleId;

    @ApiModelProperty(value = "导航ID集合")
    @NotNull
    private List<Long> ids;

}
