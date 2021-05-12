package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.WalletTransferRo;
import com.minlia.module.wallet.entity.SysWalletTransferEntity;

/**
 * <p>
 * 钱包-转账 服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-12
 */
public interface SysWalletTransferService extends IService<SysWalletTransferEntity> {

    boolean create(WalletTransferRo transferRo);

}
