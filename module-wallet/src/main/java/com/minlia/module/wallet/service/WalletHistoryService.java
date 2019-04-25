package com.minlia.module.wallet.service;

import com.minlia.module.wallet.bean.entity.WalletHistory;
import com.minlia.module.wallet.bean.ro.WalletURO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WalletHistoryService {

    /**
     * 创建
     * @param ro
     * @return
     */
    int create(WalletURO ro, Long walletId);

    /**
     * 根据钱包ID查询
     * @param walletId
     * @return
     */
    List<WalletHistory> queryByWalletId(Long walletId);

}
