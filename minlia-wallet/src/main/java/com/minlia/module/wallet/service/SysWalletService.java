package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.WalletTransferRo;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.entity.SysWalletEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    boolean update(WalletUro uro);

    SysWalletEntity getByUid(Long uid);

}