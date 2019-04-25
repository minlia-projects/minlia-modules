package com.minlia.module.address.ro;

import com.minlia.cloud.body.Body;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by garen on 2017/6/30.
 */
@Data
@ApiModel("用户地址-添加")
public class AddressCRO implements Body {

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postalCode;

}
