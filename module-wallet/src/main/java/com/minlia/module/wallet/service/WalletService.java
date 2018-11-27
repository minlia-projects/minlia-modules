//package com.minlia.module.wallet.v1.service;
//
//import com.minlia.boot.v1.service.IService;
//import com.minlia.module.wallet.v1.bean.WalletRequestBody;
//import com.minlia.module.wallet.v1.domain.Wallet;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * 钱包
// */
//@Transactional
//public interface WalletService extends IService<Wallet> {
//
//    String ENTITY = "wallet";
//
//    String ENTITY_CREATE = ENTITY + ".create";
//    String ENTITY_UPDATE = ENTITY + ".update";
//    String ENTITY_DELETE = ENTITY + ".delete";
//    String ENTITY_READ = ENTITY + ".read";
//    String ENTITY_SEARCH = ENTITY + ".search";
//
//    /**
//     * 创建用户初始钱包
//     *
//     * @param userId
//     * @return
//     */
//    Wallet init(Long userId);
//
//    /**
//     * 钱包更新(收款、付款、提现申请、提现结算、退款)
//     *
//     * @param requestBody
//     * @return
//     */
//    Wallet update(WalletRequestBody requestBody);
//
//    /**
//     * 个人钱包
//     *
//     * @return
//     */
//    Wallet findOneByOwner();
//
//}
