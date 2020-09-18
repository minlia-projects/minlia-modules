package com.minlia.module.i18n.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 国际化
 * </p>
 *
 * @author garen
 * @since 2020-08-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_i18n")
@ApiModel(value = "I18nEntity对象", description = "国际化")
public class I18nEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "编号")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "值")
    @TableField("value")
    private String value;

    @TableField("locale")
    private String locale;

    @ApiModelProperty(value = "语言")
    @TableField("language")
    private String language;

}