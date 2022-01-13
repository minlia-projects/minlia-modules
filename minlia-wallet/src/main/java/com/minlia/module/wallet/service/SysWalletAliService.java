package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.SysWalletAliSro;
import com.minlia.module.wallet.entity.SysWalletAliEntity;

/**
 * <p>
 * 钱包-支付宝 服务类
 * </p>
 *
 * @author garen
 * @since 2022-01-12
 */
public interface SysWalletAliService extends IService<SysWalletAliEntity> {

    SysWalletAliEntity getByUid(Long uid);

    Boolean save(SysWalletAliSro sro);

}