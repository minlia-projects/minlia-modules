package com.minlia.module.rebecca.navigation.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.minlia.module.common.enumeration.TreeNodeTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 导航
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_navigation")
@ApiModel(value = "SysNavigationEntity对象", description = "导航")
public class SysNavigationEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "类型：FOLDER-根、LEAF-叶")
    @TableField("type")
    private TreeNodeTypeEnum type;

    @ApiModelProperty(value = "名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "国际化KEY")
    @TableField("i18n")
    private String i18n;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "路由")
    @TableField("state")
    private String state;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "隐藏标识")
    @TableField("hide_flag")
    private Boolean hideFlag;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

    @TableField(exist = false/*, select = false, whereStrategy = FieldStrategy.NEVER*/)
    private List<SysNavigationEntity> children;

}
