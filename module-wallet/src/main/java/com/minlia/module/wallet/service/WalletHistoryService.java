//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.module.wallet.v1.bean.WalletRequestBody;
//import com.minlia.module.wallet.v1.domain.WalletHistory;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional
//public interface WalletHistoryService {
//
//    String ENTITY = "wallet";
//
//    String ENTITY_CREATE = ENTITY + ".create";
//    String ENTITY_UPDATE = ENTITY + ".update";
//    String ENTITY_DELETE = ENTITY + ".delete";
//    String ENTITY_READ = ENTITY + ".read";
//    String ENTITY_SEARCH= ENTITY + ".search";
//
//    /**
//     * 创建
//     * @param requestBody
//     * @return
//     */
//    WalletHistory create(WalletRequestBody requestBody,Long walletId);
//
//    /**
//     * 根据钱包ID查询
//     * @param walletId
//     * @return
//     */
//    List<WalletHistory> findByWalletId(Long walletId);
//
//}
