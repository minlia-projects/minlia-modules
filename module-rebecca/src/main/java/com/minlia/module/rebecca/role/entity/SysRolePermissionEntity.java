package com.minlia.module.rebecca.role.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色-权限点关系映射表
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_permission")
@ApiModel(value = "SysRolePermissionEntity对象", description = "角色-权限点关系映射表")
public class SysRolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;

}
