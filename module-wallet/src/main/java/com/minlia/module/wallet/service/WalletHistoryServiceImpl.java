//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.module.wallet.v1.bean.WalletRequestBody;
//import com.minlia.module.wallet.v1.domain.WalletHistory;
//import com.minlia.module.wallet.v1.mapper.WalletHistoryRepository;
//import org.dozer.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by user on 3/13/17.
// */
//@Service
//public class WalletHistoryServiceImpl implements WalletHistoryService {
//
//    @Autowired
//    private Mapper mapper;
//
//    @Autowired
//    private WalletHistoryRepository repository;
//
//    public WalletHistory create(WalletRequestBody requestBody,Long walletId){
//        WalletHistory history = mapper.map(requestBody, WalletHistory.class);
//        history.setWalletId(walletId);
//        return repository.save(history);
//    }
//
//    @Override
//    public List<WalletHistory> findByWalletId(Long walletId) {
//        return repository.findByWalletId(walletId);
//    }
//
//}
