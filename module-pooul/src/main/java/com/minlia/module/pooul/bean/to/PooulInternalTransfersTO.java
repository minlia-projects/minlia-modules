package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内部转账
 * Created by garen on 2018/9/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulInternalTransfersTO {

    /**
     * 付款方商户编号,可以是平台商户或入驻商户LoginThirdPartyServiceImpl.jav
     * 必填
     */
    private String payer_merchant_id;

    /**
     * 收款方商户编号,可以是平台商户或入驻商户
     * 必填
     */
    private String payee_merchant_id;

    /**
     * 转账金额，单位为分
     * 必填
     */
    private Integer amount;

    /**
     * 转账手续费，单位为分
     * 可选
     */
    private Integer trade_fee;

    /**
     * 划账业务类型，客户自定义
     * 可选
     */
    private String transfer_type;

    /**
     * 转账凭证，可以是会计凭证编号，或订单号
     * 必填
     */
    private String voucher;

    /**
     * 备注，对这笔转账的说明
     * 可选
     */
    private String remarks;

    /**
     * 操作员，可以传user_id
     * 可选
     */
    private String op_user_id;

}
