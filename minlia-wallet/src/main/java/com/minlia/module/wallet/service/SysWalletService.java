package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.entity.SysWalletEntity;

/**
 * <p>
 * 钱包 服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
public interface SysWalletService extends IService<SysWalletEntity> {

    SysWalletEntity init(Long uid);

    SysWalletEntity update(WalletUro uro);

//    SysWalletEntity recharge(WalletRechargeRo ro);

    SysWalletEntity getByUid(Long uid);

}
