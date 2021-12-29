package com.minlia.module.rebecca.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.common.enums.TreeNodeTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@ApiModel(value = "SysMenuEntity对象", description = "菜单")
public class SysMenuEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "国际化key")
    @TableField("i18n")
    private String i18n;

    @ApiModelProperty(value = "语言环境")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "类型：根、叶")
    @TableField("type")
    private TreeNodeTypeEnum type;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "路由")
    @TableField("link")
    private String link;

    @ApiModelProperty(value = "排序值")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "复用标识")
    @TableField("reuse_flag")
    private Boolean reuseFlag;

    @ApiModelProperty(value = "快捷导航标识")
    @TableField("shortcut_flag")
    private Boolean shortcutFlag;

    @ApiModelProperty(value = "面包线标识")
    @TableField("breadcrumb_Flag")
    private Boolean breadcrumbFlag;

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


}
