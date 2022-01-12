package com.minlia.module.wallet.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.bean.WalletWithdrawApplyRo;
import com.minlia.module.wallet.bean.WalletWithdrawApprovalRo;
import com.minlia.module.wallet.config.WalletConfig;
import com.minlia.module.wallet.constant.WalletCode;
import com.minlia.module.wallet.entity.SysWalletEntity;
import com.minlia.module.wallet.entity.SysWalletWithdrawEntity;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import com.minlia.module.wallet.enums.WithdrawStatusEnum;
import com.minlia.module.wallet.mapper.SysWalletWithdrawMapper;
import com.minlia.module.wallet.service.SysWalletService;
import com.minlia.module.wallet.service.SysWalletWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 钱包-提现 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Service
@RequiredArgsConstructor
public class SysWalletWithdrawServiceImpl extends ServiceImpl<SysWalletWithdrawMapper, SysWalletWithdrawEntity> implements SysWalletWithdrawService {

    private final WalletConfig walletConfig;
    private final SysWalletService sysWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysWalletWithdrawEntity apply(WalletWithdrawApplyRo applyRo) {
        SysWalletEntity walletEntity = sysWalletService.getByUid(SecurityContextHolder.getUid());
        ApiAssert.state(NumberUtil.isGreaterOrEqual(applyRo.getAmount(), walletConfig.getMinimumWithdrawalAmount()), WalletCode.Message.LESS_THAN_MINIMUM_WITHDRAWAL_AMOUNT);
        ApiAssert.state(NumberUtil.isGreaterOrEqual(walletEntity.getBalance(), applyRo.getAmount()), WalletCode.Message.BALANCE_NOT_ENOUGH);

        //插入数据
        SysWalletWithdrawEntity withdrawEntity = SysWalletWithdrawEntity.builder()
                .uid(SecurityContextHolder.getUid())
                .walletId(walletEntity.getId())
                .channel(applyRo.getChannel())
                .amount(applyRo.getAmount())
                .payee(applyRo.getPayee())
                .account(applyRo.getAccount())
                .status(WithdrawStatusEnum.PENDING)
                .build();
        this.save(withdrawEntity);

        //更新钱包冻结金额,可用余额
        WalletUro walletUro = WalletUro.builder()
                .uid(walletEntity.getUid())
                .type(WalletOperationTypeEnum.FREEZE)
                .amount(applyRo.getAmount())
                .businessType("WITHDRAW_APPLY")
                .businessId(withdrawEntity.getId().toString())
                .build();
        sysWalletService.update(walletUro);

        return withdrawEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean approval(WalletWithdrawApprovalRo approvalRo) {
        SysWalletWithdrawEntity withdrawEntity = this.getById(approvalRo.getId());
        ApiAssert.notNull(withdrawEntity, WalletCode.Message.WITHDRAW_RECOED_NOT_EXISTS);
        ApiAssert.state(withdrawEntity.getStatus().equals(WithdrawStatusEnum.PENDING), WalletCode.Message.WITHDRAW_STATUS_ERROR);

        if (approvalRo.getPass()) {
            withdrawEntity.setStatus(WithdrawStatusEnum.SETTLED);

            //更新钱包冻结金额,可用余额
            WalletUro walletUro = WalletUro.builder()
                    .uid(withdrawEntity.getUid())
                    .type(WalletOperationTypeEnum.FREEZE_SETTLED)
                    .amount(withdrawEntity.getAmount())
                    .businessType("WITHDRAW_SETTLED")
                    .businessId(withdrawEntity.getId().toString())
                    .build();
            sysWalletService.update(walletUro);
        } else {
            withdrawEntity.setStatus(WithdrawStatusEnum.REJECTED);

            //更新钱包冻结金额,可用余额
            //WalletUro walletUro = WalletUro.builder()
            //        .uid(withdrawEntity.getUid())
            //        .type(WalletOperationTypeEnum.THAW)
            //        .amount(withdrawEntity.getAmount())
            //        .businessType("WITHDRAW_CANCEL")
            //        .businessId(withdrawEntity.getId().toString())
            //        .build();
            //sysWalletService.update(walletUro);
        }

        withdrawEntity.setSettledAmount(approvalRo.getSettledAmount());
        withdrawEntity.setRemark(approvalRo.getRemark());
        return this.updateById(withdrawEntity);
    }

}