package com.minlia.module.address.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户地址
 * Created by garen on 2018/11/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDO extends AbstractEntity {

    /**
     * 用户GUID
     */
    private String guid;

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

    /**
     * 默认地址
     */
    private Boolean def;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
