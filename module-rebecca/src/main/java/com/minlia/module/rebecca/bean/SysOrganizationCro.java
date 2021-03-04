package com.minlia.module.rebecca.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 组织 保存 请求体
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysOrganizationSro", description = "组织")
public class SysOrganizationCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级ID", required = true)
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    @Size(max = 100)
    private String name;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}