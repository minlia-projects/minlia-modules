package com.minlia.module.shipping.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.shipping.enums.ExpressAddressTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收货地址
 * </p>
 *
 * @author garen
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_shipping_address")
@ApiModel(value = "SysShippingAddressEntity对象", description = "收货地址")
public class ShippingAddressEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户UID")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "类型")
    @TableField("`type`")
    private ExpressAddressTypeEnum type;

    @ApiModelProperty(value = "联系人")
    @TableField("contacts")
    private String contacts;

    @ApiModelProperty(value = "别名")
    @TableField("alias")
    private String alias;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("district")
    private String district;

    @ApiModelProperty(value = "街道")
    @TableField("street")
    private String street;

    @ApiModelProperty(value = "详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "邮编")
    @TableField("postcode")
    private String postcode;

    @ApiModelProperty(value = "手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "固定电话")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "是否默认")
    @TableField("def_flag")
    private Boolean defFlag;

    @ApiModelProperty(value = "是否启用")
    @TableField("dis_flag")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}