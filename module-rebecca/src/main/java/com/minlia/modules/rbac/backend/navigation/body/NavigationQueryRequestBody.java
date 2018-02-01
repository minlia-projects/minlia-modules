package com.minlia.modules.rbac.backend.navigation.body;

import com.minlia.module.data.body.QueryRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "导航-查询")
public class NavigationQueryRequestBody implements QueryRequestBody {

    private Long id;

    private Long parentId;

    private Long roleId;

    private String name;

    @ApiModelProperty(value = "是否有效")
    private Boolean enabled;

}
