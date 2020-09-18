package com.minlia.module.rebecca.role.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@ApiModel(value = "SysRoleSro", description = "角色")
public class SysRoleSro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @Min(1)
    private Long id;

    @ApiModelProperty(value = "编码")
    @NotBlank
    @Size(max = 20)
    private String code;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "数据范围类型")
    private Integer dsType;

    @ApiModelProperty(value = "数据范围自定义值")
    private Integer dsScope;

    @ApiModelProperty(value = "访问域名")
    @Size(max = 200)
    private String accessDomain;

    @ApiModelProperty(value = "备注")
    @Size(max = 200)
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    private Boolean delFlag;

}