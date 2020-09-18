package com.minlia.module.bible.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_bible_item")
@ApiModel(value = "BibleItemEntity对象", description = "数据字典")
public class BibleItemEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父编码")
    @TableField("parent_code")
    private String parentCode;

    @ApiModelProperty(value = "编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "说明")
    @TableField("notes")
    private String notes;

    @ApiModelProperty(value = "排序")
    @TableField("sorts")
    private Integer sorts;

    @ApiModelProperty(value = "扩展字段1")
    @TableField("attribute1")
    private String attribute1;

    @ApiModelProperty(value = "扩展字段2")
    @TableField("attribute2")
    private String attribute2;

    @ApiModelProperty(value = "扩展字段3")
    @TableField("attribute3")
    private String attribute3;

}