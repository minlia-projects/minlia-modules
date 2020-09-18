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
 * 角色-导航关系映射表
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_navigation")
@ApiModel(value = "SysRoleNavigationEntity对象", description = "角色-导航关系映射表")
public class SysRoleNavigationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
    private Long roleId;

    @TableField("navigation_id")
    private Long navigationId;

}
