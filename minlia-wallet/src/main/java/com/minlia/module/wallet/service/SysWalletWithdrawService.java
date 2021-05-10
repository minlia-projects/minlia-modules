package com.minlia.module.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wallet.bean.WalletWithdrawApplyRo;
import com.minlia.module.wallet.bean.WalletWithdrawApprovalRo;
import com.minlia.module.wallet.entity.SysWalletWithdrawEntity;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-提现 服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
public interface SysWalletWithdrawService extends IService<SysWalletWithdrawEntity> {

    SysWalletWithdrawEntity apply(WalletWithdrawApplyRo applyRo);

    boolean approval(WalletWithdrawApprovalRo approvalRo);

}