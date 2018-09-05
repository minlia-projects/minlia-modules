package com.minlia.module.pooul.bean.dto;

import lombok.Data;

/**
 * Created by garen on 2018/9/5.
 */
@Data
public class PooulMerchantCreateDTO extends PooulDTO {

//    {
//        "code": 0,
//        "msg": "success",
//        "data":{
//            "_id": "9646592604059242",
//            "_type": "Ns::TenantMerchant",
//            "arrears": false,
//            "business": {
//                "short_name": "测试"
//            },
//            "created_at": 1536157355,
//            "level_code": "00a018002",
//            "license_type": 3,
//            "note": "测试",
//            "owner": {
//                "name": "测试",
//                "idcard_type": 1
//            },
//            "parent_id": "2849928048545130",
//            "platform_merchant_id": "2849928048545130",
//            "status": 5,
//            "updated_at": 1536157355
//        }
//        "time_elapsed": 0.5632
//    }

    private Data data;

    @lombok.Data
    class Data {
        private String _id;
        private String _type;
        private Boolean arrears;
    }

}
