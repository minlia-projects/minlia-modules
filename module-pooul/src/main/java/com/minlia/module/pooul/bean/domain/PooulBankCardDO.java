package com.minlia.module.pooul.bean.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/9/21.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulBankCardDO extends AbstractEntity{

    /**
     * 商户ID
     */
    @JsonProperty("merchant_id")
    private String merchantId;

    /**
     * 记录ID
     */
    @JsonProperty("_id")
    private Long recordId;

    /**
     * 账户类型，0 个人、1 企业，默认为0
     * 必填
     */
    @JsonProperty("account_type")
    private Integer accountType;

    /**
     * 户名，个人账户为真实姓名，企业账户为公司全称
     * 必填
     */
    @JsonProperty("owner_name")
    private String ownerName;

    /**
     * 银行账号， 如：6217680300228911
     * 必填
     */
    @JsonProperty("account_num")
    private String accountNum;

    /**
     * 银行全称，如：中信银行股份有限公司深圳香林支行
     * 必填
     */
    @JsonProperty("bank_full_name")
    private String bankFullName;

    /**
     * 大小额联行号
     * 必填
     */
    @JsonProperty("bank_sub_code")
    private String bankSubCode;

    /**
     * 个人帐户需留银行开户预留手机号，企业账户则为联系人手机号
     */
    @JsonProperty("contact_mobile")
    private String contactMobile;

    /**
     * 开户省， 如：广东
     */
    @JsonProperty("province")
    private String province;

    /**
     * 开户市，如：深圳
     */
    @JsonProperty("urbn")
    private String urbn;

    /**
     * 开户区， 如：福田
     */
    @JsonProperty("area")
    private String area;

    /**
     * 网银互联转账时的行号
     */
    @JsonProperty("cyber_bank_code")
    private String cyberBankCode;

    /**
     * 是否民生银行，true或false，默认：false
     */
    @JsonProperty("cmbc_bank")
    private Boolean cmbcBank;

}
