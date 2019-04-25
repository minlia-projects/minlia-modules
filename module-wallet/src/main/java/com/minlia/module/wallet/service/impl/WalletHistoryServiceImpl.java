package com.minlia.module.wallet.service.impl;

import com.minlia.module.wallet.bean.entity.WalletHistory;
import com.minlia.module.wallet.bean.ro.WalletURO;
import com.minlia.module.wallet.mapper.WalletHistoryMapper;
import com.minlia.module.wallet.service.WalletHistoryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 3/13/17.
 */
@Service
public class WalletHistoryServiceImpl implements WalletHistoryService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private WalletHistoryMapper walletHistoryMapper;

    public int create(WalletURO ro, Long walletId){
        WalletHistory history = mapper.map(ro, WalletHistory.class);
        history.setWalletId(walletId);
        return walletHistoryMapper.create(history);
    }

    @Override
    public List<WalletHistory> queryByWalletId(Long walletId) {
        return walletHistoryMapper.queryByWalletId(walletId);
    }

}
