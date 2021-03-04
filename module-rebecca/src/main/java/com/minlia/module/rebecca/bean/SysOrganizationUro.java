package com.minlia.module.rebecca.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 组织 修改 请求体
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Data
@ApiModel(value = "SysOrganizationUro", description = "组织")
public class SysOrganizationUro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "名称", required = true)
    @Size(max = 100)
    private String name;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}