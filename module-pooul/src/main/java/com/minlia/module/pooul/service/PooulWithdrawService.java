package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.qo.PooulCmbaYqQO;
import com.minlia.module.pooul.bean.to.PooulWithdrawTO;

/**
 * 对外转账
 * 平台商户可以使用次功能将结余对外转至银行卡，支持平台商户、入驻商户对外转账至同名银行卡，需开通民生银行银企直连转账交易功能
 * Created by garen on 2018/9/21.
 */
public interface PooulWithdrawService {

    //平台商户可以使用次功能将结余对外转至银行卡
    String POST_WITHDRAW_URL = "/v2/withdraw?merchant_id=";

    //对外转账业务明细查询
    String GET_WITHDRAW_URL = "/v2/withdraw/queries?merchant_id=";

    //查询实体账户明细
    String FUND_ACCOUNT_URL = "/cms/cmbc_yq/fund_account";

    Response withdraw(String merchantId, PooulWithdrawTO pooulWithdrawTO);

    /**
     * 对外转账状态查询
     * @param merchantId
     * @param mch_withdraw_id
     * @return
     */
    Response query(String merchantId, String mch_withdraw_id);

    /**
     * 通过此接口可以查询平台商户在民生银行开通银企直连绑定的实体账户资金流水明细，仅能查询开通民生银企直连的平台商户
     * @param qo
     * @return
     */
    Response fundAccount(PooulCmbaYqQO qo);

}
