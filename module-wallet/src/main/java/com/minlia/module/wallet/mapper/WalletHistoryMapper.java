package com.minlia.module.wallet.mapper;


import com.minlia.module.wallet.bean.entity.WalletHistory;

import java.util.List;

public interface WalletHistoryMapper {

    int create(WalletHistory walletHistory);

    int update(WalletHistory walletHistory);

    int delete(Long id);

    List<WalletHistory> queryByWalletId(Long walletId);

}