package com.minlia.module.rebecca.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色-用户关系映射表
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
@ApiModel(value = "SysRoleUserEntity对象", description = "角色-用户关系映射表")
public class SysRoleUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private Long roleId;

    @TableField("user_id")
    private Long userId;

}
