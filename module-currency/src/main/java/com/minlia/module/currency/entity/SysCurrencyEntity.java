package com.minlia.module.currency.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 货币
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_currency")
@ApiModel(value = "SysCurrencyEntity对象", description = "货币")
public class SysCurrencyEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "符号")
    @TableField("symbol")
    private String symbol;

    @ApiModelProperty(value = "排序")
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