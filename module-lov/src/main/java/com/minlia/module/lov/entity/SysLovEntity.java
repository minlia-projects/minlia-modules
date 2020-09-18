package com.minlia.module.lov.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * LOV表
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_lov")
@ApiModel(value = "SysLovEntity对象", description = "LOV表")
public class SysLovEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属租户")
    @TableField("tenant_id")
    private Integer tenantId;

    @ApiModelProperty(value = "编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("`name`")
    private String name;

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
