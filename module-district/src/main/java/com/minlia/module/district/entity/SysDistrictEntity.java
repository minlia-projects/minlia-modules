package com.minlia.module.district.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.district.enumeration.DistrictLevel;
import com.minlia.module.i18n.enums.LocaleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 区域
 * </p>
 *
 * @author garen
 * @since 2021-04-06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_district")
@ApiModel(value = "SysDistrictEntity对象", description = "区域")
public class SysDistrictEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级")
    @TableField("parent")
    private String parent;

    @ApiModelProperty(value = "区域编码")
    @TableField("adcode")
    private String adcode;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "级别")
    @TableField("level")
    private DistrictLevel level;

    @ApiModelProperty(value = "有子集")
    @TableField("has_child")
    private Boolean hasChild;

    @ApiModelProperty(value = "语言")
    @TableField("locale")
    private LocaleEnum locale;

    @ApiModelProperty(value = "编码全")
    @TableField("fullcode")
    private String fullcode;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "坐标")
    @TableField("center")
    private String center;

    @ApiModelProperty(value = "城市编码")
    @TableField("citycode")
    private String citycode;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}