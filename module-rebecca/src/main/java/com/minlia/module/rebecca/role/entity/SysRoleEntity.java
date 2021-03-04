package com.minlia.module.rebecca.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 角色
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
@TableName("sys_role")
@ApiModel(value = "SysRoleEntity对象", description = "角色")
public class SysRoleEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "数据范围类型")
    @TableField("dp_type")
    private Integer dpType;

    @ApiModelProperty(value = "数据范围自定义值")
    @TableField("dp_scope")
    private String dpScope;

    @ApiModelProperty(value = "访问域名")
    @TableField("access_domain")
    private String accessDomain;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}