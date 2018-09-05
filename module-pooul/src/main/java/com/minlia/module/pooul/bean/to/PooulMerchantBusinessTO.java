package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/9/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulMerchantBusinessTO {

    /**
     * 商户简称，经营名称，如：平克文化
     * 必填
     */
    private String short_name;

    /**
     * 服务电话，如：0755-82857285
     * 可选
     */
    private String service_call;

    /**
     * 行政区划代码，参考民政部最新的区划代码，如：440309
     * 可选
     */
    private String area_code;

    /**
     * 经营地址所在省，如：广东省
     * 可选
     */
    private String province;

    /**
     * 经营地址所在地级市，如：深圳市
     * 可选
     */
    private String urbn;

    /**
     * 经营地址所在区，如：龙华新区
     * 可选
     */
    private String area;

    /**
     * 经营详细地址，不含省市区，如：龙华新区大浪办事处大浪社区新围村71栋101
     * 可选
     */
    private String address;

}
