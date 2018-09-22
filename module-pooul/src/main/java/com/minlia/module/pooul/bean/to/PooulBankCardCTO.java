package com.minlia.module.pooul.bean.to;

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
public class PooulBankCardCTO {

    /**
     * 账户类型，0 个人、1 企业，默认为0
     * 必填
     */
    private Integer account_type;

    /**
     * 户名，个人账户为真实姓名，企业账户为公司全称
     * 必填
     */
    private String owner_name;

    /**
     * 银行账号， 如：6217680300228911
     * 必填
     */
    private String account_num;

    /**
     * 银行全称，如：中信银行股份有限公司深圳香林支行
     * 必填
     */
    private String bank_full_name;

    /**
     * 大小额联行号
     * 必填
     */
    private String bank_sub_code;

    /**
     * 个人帐户需留银行开户预留手机号，企业账户则为联系人手机号
     */
    private String contact_mobile;

    /**
     * 开户省， 如：广东
     */
    private String province;

    /**
     * 开户市，如：深圳
     */
    private String urbn;

    /**
     * 开户区， 如：福田
     */
    private String area;

    /**
     * 网银互联转账时的行号
     */
    private String cyber_bank_code;

    /**
     * 是否民生银行，true或false，默认：false
     */
    private Boolean cmbc_bank;

}
