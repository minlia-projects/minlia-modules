package com.minlia.module.wallet.vo;


import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wallet.enumeration.BankCardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 银行卡
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankCardVo extends AbstractEntity {

    /**
     * 用户ID
     */
    private String guid;

    /**
     * 银行账号类型
     */
    private BankCardType type;

    /**
     * 开户人
     */
    private String holder;

    /**
     * 卡号
     */
    private String number;

    /**
     * 提现账号
     */
    private Boolean isWithdraw;

    /**
     * 联行号
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 支行名称
     */
    private String branchName;

    /**
     * 省
     */
    private String province;

}
