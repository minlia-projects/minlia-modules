package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 个人商户创建请求体
 * Created by garen on 2018/9/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulMerchantPersonalCTO {

    /**
     * 商户备注信息
     */
    private String note;

    /**
     * 商户经营信息参数集合，请参考Merchant business 参数
     */
    private PooulMerchantBusinessTO business;

    /**
     * 所有人/法人信息参数集合，license_type为3时为所有人信息，license_type为1、2时为营业执照法人信息，请参考Merchant owner 参数
     * 必填
     */
    private PooulMerchantOwnerTO owner;

}
