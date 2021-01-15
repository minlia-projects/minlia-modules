package com.minlia.module.shipping.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author garen
 */
@Data
@ApiModel
public class ShippingAddressSro {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街道")
    private String street;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "邮编")
    private String postcode;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "固定电话")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否默认")
    @NotNull
    private Boolean defFlag;

    @ApiModelProperty(value = "是否启用")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    private Boolean delFlag;

}