package com.minlia.module.advertisement.entity;

import com.minlia.module.advertisement.enums.SysAdvertisementTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告
 * </p>
 *
 * @author garen
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_advertisement")
@ApiModel(value = "SysAdvertisementEntity对象", description = "广告")
public class SysAdvertisementEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "广告ID")
    @TableField("aid")
    private Long aid;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private SysAdvertisementTypeEnum type;

    @ApiModelProperty(value = "链接地址")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "封面")
    @TableField("cover")
    private String cover;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "扩展字段")
    @TableField("attribute1")
    private String attribute1;

    @ApiModelProperty(value = "扩展字段")
    @TableField("attribute2")
    private String attribute2;

    @ApiModelProperty(value = "扩展字段")
    @TableField("attribute3")
    private String attribute3;

    @ApiModelProperty(value = "扩展字段")
    @TableField("attribute4")
    private String attribute4;

    @ApiModelProperty(value = "扩展字段")
    @TableField("attribute5")
    private String attribute5;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;


}
