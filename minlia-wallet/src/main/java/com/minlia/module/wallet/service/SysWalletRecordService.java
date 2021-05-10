package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.entity.SysWalletRecordEntity;

/**
 * <p>
 * 钱包-记录 服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
public interface SysWalletRecordService extends IService<SysWalletRecordEntity> {

    void create(Long walletId, WalletUro uro);

}