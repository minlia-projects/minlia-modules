package com.minlia.module.lov.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * LOV值集表
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_lov_item")
@ApiModel(value = "SysLovItemEntity对象", description = "LOV值集表")
public class SysLovItemEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private Integer tenantId;

    @ApiModelProperty(value = "值集ID")
    @TableField("lov_id")
    private Long lovId;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "语言环境")
    @TableField("locale")
    private LocaleEnum locale;

    @ApiModelProperty(value = "排序（升序）")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "禁用标记")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "删除标记")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}