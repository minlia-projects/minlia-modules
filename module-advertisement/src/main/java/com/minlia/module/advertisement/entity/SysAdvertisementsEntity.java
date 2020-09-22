package com.minlia.module.advertisement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 广告集
 * </p>
 *
 * @author garen
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_advertisements")
@ApiModel(value = "SysAdvertisementsEntity对象", description = "广告集")
public class SysAdvertisementsEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "平台")
    @TableField("platform")
    private String platform;

    @ApiModelProperty(value = "投放位置")
    @TableField("position")
    private String position;

    @ApiModelProperty(value = "横纵比")
    @TableField("aspectRatio")
    private String aspectRatio;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

    @TableField(exist = false)
    private List<SysAdvertisementEntity> advertisements;

}