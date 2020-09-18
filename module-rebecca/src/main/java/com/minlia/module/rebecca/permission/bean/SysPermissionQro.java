package com.minlia.module.rebecca.permission.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "SysPermissionQro对象", description = "权限点")
public class SysPermissionQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "描述")
    @Size(max = 100)
    private String desc;

    @ApiModelProperty(value = "禁用标识")
    private Boolean disFlag;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
//    private Long uid;

}