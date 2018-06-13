package com.minlia.module.lbsyun.body.response;

import lombok.Data;

/**
 * Created by garen on 2018/6/13.
 */
@Data
public class AddressComponent {

    /**
     * 国家
     */
    private String country;

    /**
     * 国家代码
     */
    private Integer country_code;

    /**
     * 省名
     */
    private String province;

    /**
     * 城市名
     */
    private String city;

    private String city_level;

    /**
     * 区县名
     */
    private String district;

    /**
     * 乡镇名
     */
    private String town;

    /**
     * 街道名（行政区划中的街道层级）
     */
    private String street;

    /**
     * 街道门牌号
     */
    private String street_number;

    /**
     * 行政区划代码 adcode映射表
     */
    private Integer adcode;

    /**
     * 相对当前坐标点的方向，当有门牌号的时候返回数据
     */
    private String direction;

    /**
     * 相对当前坐标点的距离，当有门牌号的时候返回数据
     */
    private String distance;

//    addressComponent: {
//        country: "Japan",
//        country_code: 26000,
//        country_code_iso: "JPN",
//        country_code_iso2: "JP",
//        province: "Tokyo",
//        city: "Minato",
//        city_level: 1,
//        district: "",
//        town: "",
//        adcode: "0",
//        street: "東京都港区芝公園4-2-8",
//        street_number: "",
//        direction: "附近",
//        distance: "40"
//    }

}
