package com.minlia.modules.rebecca.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.modules.rebecca.enumeration.NavigationType;
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
public class NavigationQO extends QueryRequest {

    private Long id;

    private Long parentId;

    private Long roleId;

    private NavigationType type;

    private String name;

    @ApiModelProperty(value = "展示/隐藏")
    private Boolean display;

    private boolean isOneLevel;

}
