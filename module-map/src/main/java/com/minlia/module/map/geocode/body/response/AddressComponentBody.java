package com.minlia.module.map.geocode.body.response;

import lombok.Data;

/**
 * Created by garen on 2018/5/11.
 */
@Data
public class AddressComponentBody {

    private String country;
    private String province;
    /**
     * 直辖市：北京、天津、上海、重庆  返回类型为[]，其它为String
     */
    private Object city;
    private String citycode;
    private String district;
    private String adcode;
    private String township;
    private String towncode;

//    addressComponent: {
//        country: "中国",
//                province: "广东省",
//                city: "深圳市",
//                citycode: "0755",
//                district: "南山区",
//                adcode: "440305",
//                township: "蛇口街道",
//                towncode: "440305005000",
//                neighborhood: {
//            name: "米兰公寓",
//                    type: "商务住宅;住宅区;住宅小区"
//        },
//        building: {
//            name: [ ],
//            type: [ ]
//        },
//        streetNumber: {
//            street: "招商路",
//                    number: "86-1号",
//                    location: "113.927659,22.4907725",
//                    direction: "南",
//                    distance: "32.1923"
//        },
//        businessAreas: [
//        {
//            location: "113.92530663086345,22.49007360027192",
//                    name: "蛇口",
//                id: "440305"
//        },
//        {
//            location: "113.95071633333332,22.514798891666672",
//                    name: "深圳湾",
//                id: "440305"
//        },
//        {
//            location: "113.93347171739138,22.50731701811594",
//                    name: "后海",
//                id: "440305"
//        }
//]
//    }
}
