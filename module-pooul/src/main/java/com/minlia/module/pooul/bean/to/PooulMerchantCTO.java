package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by garen on 2018/9/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulMerchantCTO implements Serializable {

    /**
//     * 商户类型，固定值为 3
     * 商户类型，创建入驻商户时不需要这个参数，创建普通商户时固定填1
     * 必填
     */
    private Integer merchant_type;

    /**
     * 平台商户编号，固定值为普尔为你开通的平台商户编号，如：7590462217569167
     * 必填
     */
    private String platform_merchant_id;

    /**
     * 父级商户编号，如为平台商户下属一级商户则为平台商户编号，如为一级一下商户则为该父级商户编号，如：7590462217569167
     * 必填
     */
    private String parent_id;

    /**
     * 平台商户自定义商户编号，如：988765
     */
    private String partner_mch_id;

    /**
     * 商户备注信息
     */
    private String note;

    /**
     * 营业类型：1. 企业; 2. 个体工商户，3. 个人
     * 必填
     */
    private Integer license_type;

    /**
     * 是否允许结余为负，true为允许，false为不允许，默认为 false
     */
    private Boolean arrears;

    /**
     * 商户经营信息参数集合，请参考Merchant business 参数
     */
    private PooulMerchantBusinessTO business;

    /**
     * 营业执照信息参数集合，license_type为3时不需传，请参考Merchant corporate 参数
     */
    private PooulMerchantCorporateTO corporate;

    /**
     * 所有人/法人信息参数集合，license_type为3时为所有人信息，license_type为1、2时为营业执照法人信息，请参考Merchant owner 参数
     * 必填
     */
    private PooulMerchantOwnerTO owner;



//    /**
//     * 商户类型，固定值为 3
//     * 必填
//     */
//    private Integer merchantType;
//
//    /**
//     * 平台商户编号，固定值为普尔为你开通的平台商户编号，如：7590462217569167
//     * 必填
//     */
//    private String platformMerchantId;
//
//    /**
//     * 父级商户编号，如为平台商户下属一级商户则为平台商户编号，如为一级一下商户则为该父级商户编号，如：7590462217569167
//     * 必填
//     */
//    private String parentId;
//
//    /**
//     * 平台商户自定义商户编号，如：988765
//     */
//    private String partnerMchId;
//
//    /**
//     * 商户备注信息
//     */
//    private String note;
//
//    /**
//     * 营业类型：1. 企业; 2. 个体工商户，3. 个人
//     * 必填
//     */
//    private Integer licenseType;
//
//    /**
//     * 是否允许结余为负，true为允许，false为不允许，默认为 false
//     */
//    private Boolean arrears;
//
//    /**
//     * 商户经营信息参数集合，请参考Merchant business 参数
//     */
//    private String business;
//
//    /**
//     * 营业执照信息参数集合，license_type为3时不需传，请参考Merchant corporate 参数
//     */
//    private String corporate;
//
//    /**
//     * 所有人/法人信息参数集合，license_type为3时为所有人信息，license_type为1、2时为营业执照法人信息，请参考Merchant owner 参数
//     * 必填
//     */
//    private String owner;

}
