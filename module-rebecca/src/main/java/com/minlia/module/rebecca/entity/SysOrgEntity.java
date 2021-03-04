package com.minlia.module.rebecca.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author garen
 * @since 2021-03-02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_org")
@ApiModel(value = "SysOrgEntity", description = "组织")
public class SysOrgEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序值")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}