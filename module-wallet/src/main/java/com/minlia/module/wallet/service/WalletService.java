package com.minlia.module.wallet.service;

import com.minlia.module.wallet.bean.entity.Wallet;
import com.minlia.module.wallet.bean.ro.WalletRechargeRO;
import com.minlia.module.wallet.bean.ro.WalletURO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 钱包
 */
@Transactional
public interface WalletService {

    String ENTITY = "wallet";
    String ENTITY_CREATE = ENTITY + ".create";
    String ENTITY_UPDATE = ENTITY + ".update";
    String ENTITY_DELETE = ENTITY + ".delete";
    String ENTITY_READ = ENTITY + ".read";
    String ENTITY_SEARCH = ENTITY + ".search";

    Wallet init(String guid);

    Wallet update(WalletURO ro);

    Wallet recharge(WalletRechargeRO ro);

    Wallet queryByGuid(String guid);

}
