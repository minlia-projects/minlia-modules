package com.minlia.module.bible.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_bible")
@ApiModel(value = "BibleEntity对象", description = "数据字典")
public class BibleEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "图标")
    @TableField("notes")
    private String notes;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

}
