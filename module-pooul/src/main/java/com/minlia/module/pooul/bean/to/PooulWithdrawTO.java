package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/9/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulWithdrawTO {

    /**
     * 商户发起对外转账的单号，需保证每一个merchant_id唯一
     * 必填
     */
    private String mch_withdraw_id;

    /**
     * 1 为对外转账到绑定银行卡，现在只能传1
     * 必填
     */
    private int withdraw_type;

    /**
     * 对外转账的银行卡ID，请先管理商户的银行卡
     * 必填
     */
    private Long bank_card_id;

    /**
     * 对外转账所采取的汇路，如果是民生银行卡则使用 0:本地；1：异地；如果非民生银行卡使用 2:小额; 3大额; 5:网银互联;
     * 必填
     */
    private int local_flag;

    /**
     * 对外转账金额，单位为分，如：100代表1元
     * 必填
     */
    private int amount;

    /**
     * 对外转账手续费，单位为分，如：100代表1元
     */
    private int trade_fee;

    /**
     * 对外转账凭证，可以传订单号或财务凭证等用于标识的参数
     * 必填
     */
    private String voucher;

    /**
     * 备注
     * 必填
     */
    private String remarks;

    /**
     * 操作员编号，user_id
     * 必填
     */
    private String op_user_id;

}
