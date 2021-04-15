package com.minlia.module.rebecca.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户-关系
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_relation")
@ApiModel(value = "SysUserRelationEntity对象", description = "用户-关系")
public class SysUserRelationEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "先人")
    @TableField("ancestor")
    private Long ancestor;

    @ApiModelProperty(value = "子孙")
    @TableField("descendant")
    private Long descendant;

    @ApiModelProperty(value = "等级")
    @TableField("level")
    private Integer level;

}