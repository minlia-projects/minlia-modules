package com.minlia.module.article.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author garen
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_article")
@ApiModel(value = "SysArticleEntity对象", description = "文章")
public class SysArticleEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类目ID")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "语言环境")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "草稿标识")
    @TableField("draft_flag")
    private Boolean draftFlag;

    @ApiModelProperty(value = "禁用标识")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "阅读数")
    @TableField("read_count")
    private Long readCount;

    @ApiModelProperty(value = "封面")
    @TableField("cover")
    private String cover;

    @ApiModelProperty(value = "关键字")
    @TableField("keywords")
    private String keywords;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "扩展字段1")
    @TableField("attribute1")
    private String attribute1;

    @ApiModelProperty(value = "扩展字段2")
    @TableField("attribute2")
    private String attribute2;

    @ApiModelProperty(value = "扩展字段3")
    @TableField("attribute3")
    private String attribute3;

    @ApiModelProperty(value = "扩展字段4")
    @TableField("attribute4")
    private String attribute4;

    @ApiModelProperty(value = "扩展字段5")
    @TableField("attribute5")
    private String attribute5;

}