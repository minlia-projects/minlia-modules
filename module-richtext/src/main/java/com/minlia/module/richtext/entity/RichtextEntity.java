package com.minlia.module.richtext.entity;

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
 * 富文本
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_richtext")
@ApiModel(value="RichtextEntity对象", description="富文本")
public class RichtextEntity extends AbstractEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "编号")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "主题")
    @TableField("subject")
    private String subject;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

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
