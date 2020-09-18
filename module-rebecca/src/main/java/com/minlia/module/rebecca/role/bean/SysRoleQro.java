package com.minlia.module.rebecca.role.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
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
@ApiModel(value = "SysRoleQro", description = "角色")
public class SysRoleQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @Size(max = 20)
    private String code;

    @ApiModelProperty(value = "名称")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "数据范围类型")
    private Integer dsType;

    @ApiModelProperty(value = "数据范围自定义值")
    private Integer dsScope;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    private Boolean delFlag;

}
