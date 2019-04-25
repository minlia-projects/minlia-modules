package com.minlia.module.bankbranch.entity;


import com.minlia.module.data.entity.WithIdEntity;
import lombok.Data;

/**
 * 联行号
 */
@Data
public class BankBranch extends WithIdEntity {

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
     * 银行名称
     */
    private String bankname;

    /**
     * 支行名称
     */
    private String branchname;

    /**
     * 联行号
     */
    private String number;

    /**
     * 电话
     */
    private String phone;

    /**
     * 经度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

}
