package com.minlia.module.shipping.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.data.entity.BaseQueryEntity;
import com.minlia.module.shipping.enums.ExpressAddressTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author garen
 */
@Data
@ApiModel
public class ShippingAddressQro extends BaseQueryEntity {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户UID")
    @JsonIgnore
    private Long uid;

    @ApiModelProperty(value = "类型")
    private ExpressAddressTypeEnum type;

    @ApiModelProperty(value = "联系人")
    @Size(max = 50)
    private String contacts;

    @ApiModelProperty(value = "别名")
    @Size(max = 20)
    private String alias;

    @ApiModelProperty(value = "省")
    @Size(max = 50)
    private String province;

    @ApiModelProperty(value = "市")
    @Size(max = 50)
    private String city;

    @ApiModelProperty(value = "区")
    @Size(max = 50)
    private String district;

    @ApiModelProperty(value = "街道")
    @Size(max = 50)
    private String street;

    @ApiModelProperty(value = "详细地址")
    @Size(max = 100)
    private String address;

    @ApiModelProperty(value = "邮编")
    @Size(max = 20)
    private String postcode;

    @ApiModelProperty(value = "手机号码")
    @Size(max = 20)
    private String mobile;

    @ApiModelProperty(value = "固定电话")
    @Size(max = 20)
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @Size(max = 32)
    private String email;

    @ApiModelProperty(value = "备注")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否默认")
    private Boolean defFlag;

    @ApiModelProperty(value = "是否启用")
    private Boolean disFlag;

    @ApiModelProperty(value = "是否删除")
    private Boolean delFlag;

}