package com.minlia.module.wallet.mapper;

import com.minlia.module.wallet.bean.entity.Wallet;

public interface WalletMapper {

    void create(Wallet wallet);

    void update(Wallet wallet);

    void delete(Long id);

    Wallet queryByGuid(String guid);

}