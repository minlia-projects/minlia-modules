package com.minlia.module.lbsyun.body.response;

import lombok.Data;

/**
 * Created by garen on 2018/6/13.
 */
@Data
public class GeoCoderResponse {

    /**
     * 返回结果状态值， 成功返回0，其他值请查看下方返回码状态表。
     */
    private Integer status;

    /**
     * 经纬度坐标
     */
    private GeoCoderResult result;

    public boolean isSuccess(){
        return this.status == 0;
    }

//    {
//        status: 0,
//                result: {
//        location: {
//            lng: 139.7454149999999,
//                    lat: 35.658650898203035
//        },
//        formatted_address: "東京都港区芝公園4-2-8, Minato, Tokyo, Japan",
//                business: "",
//                addressComponent: {
//            country: "Japan",
//                    country_code: 26000,
//                    country_code_iso: "JPN",
//                    country_code_iso2: "JP",
//                    province: "Tokyo",
//                    city: "Minato",
//                    city_level: 1,
//                    district: "",
//                    town: "",
//                    adcode: "0",
//                    street: "東京都港区芝公園4-2-8",
//                    street_number: "",
//                    direction: "附近",
//                    distance: "40"
//        },
//        pois: [ ],
//        roads: [ ],
//        poiRegions: [ ],
//        sematic_description: "",
//                cityCode: 26041
//    }
//    }

}
