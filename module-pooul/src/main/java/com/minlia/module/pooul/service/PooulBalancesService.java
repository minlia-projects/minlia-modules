package com.minlia.module.pooul.service;

import com.minlia.module.pooul.bean.dto.PooulBlancesDTO;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulInternalTransfersTO;

/**
 * 结余 balances
 * 入驻商户通过微信支付、支付宝支付后，或使用网银转账至民生子账户后系统会对入账成功的交易进行记账处理;
 * 记账完成后会增加入驻商户结余，手续费差额部分会记到对应的平台商户结余中;
 * 可以查询到记账明细，结余金额，平台商户可以调用接口进行结余的转账。
 * Created by garen on 2018/9/6.
 */
public interface PooulBalancesService {

    String query_balances_url = "/cms/balances?merchant_id={merchantId}";

    String query_history_url = "/cms/balances/history?merchant_id={merchantId}";

    String internal_transfers_url = "/v2/internal_transfers?merchant_id=";

    /**
     * 查询记账账簿结余 Query balances
     * @param merchantId
     * @return
     */
    PooulBlancesDTO queryBalances(String merchantId);

    /**
     * 查询记账账簿明细 Balances list
     * @param merchantId
     * @return
     */
    Object queryHistory(String merchantId);

    /**
     * 内部转账 RSA
     * @param merchantId
     * @param transfersTO
     * @return
     */
    PooulDTO internalTransfers(String merchantId, PooulInternalTransfersTO transfersTO);

}
