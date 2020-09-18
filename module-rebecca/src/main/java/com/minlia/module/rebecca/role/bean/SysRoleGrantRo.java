package com.minlia.module.rebecca.role.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@ApiModel(value = "SysRoleGrantRo", description = "角色-授权")
public class SysRoleGrantRo {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "角色ID")
    @NotNull
    private Long roleId;

    @ApiModelProperty(value = "ID集合")
    @NotNull
    private Set<Long> ids;

}