package com.minlia.module.pooul.bean.to;

import lombok.Data;

/**
 * Created by garen on 2018/9/5.
 */
@Data
public class PooulMerchantCorporateTO {

    /**
     * 企业全称，需为营业执照上企业全称，如：深圳市平克茶业文化发展有限公司"
     * 必填
     */
    private String full_name;

    /**
     * # 非三证合一为营业执照编码，三证合一为统一社会信用代码，如：91440300398456074L"
     * 必填
     */
    private String license_num;

}
