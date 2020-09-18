package com.minlia.module.rebecca.permission.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Size;

/**
 * <p>
 * 权限点
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
@ApiModel(value = "SysPermissionEntity对象", description = "权限点")
public class SysPermissionEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @TableField("`code`")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "描述")
    @TableField("label")
    @Size(max = 100)
    private String label;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

}
