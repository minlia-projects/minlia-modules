package com.minlia.module.wallet.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.constant.WalletCode;
import com.minlia.module.wallet.entity.SysWalletEntity;
import com.minlia.module.wallet.mapper.SysWalletMapper;
import com.minlia.module.wallet.service.SysWalletRecordService;
import com.minlia.module.wallet.service.SysWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 * 钱包 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Service
@RequiredArgsConstructor
public class SysWalletServiceImpl extends ServiceImpl<SysWalletMapper, SysWalletEntity> implements SysWalletService {

    private final SysWalletRecordService sysWalletRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysWalletEntity init(Long uid) {
        SysWalletEntity walletEntity = this.getByUid(uid);
        if (Objects.isNull(walletEntity)) {
            walletEntity = SysWalletEntity.builder().uid(uid).build();
            this.save(walletEntity);
        }
        return walletEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(WalletUro uro) {
        SysWalletEntity walletEntity = this.getByUid(uro.getUid());
        switch (uro.getType()) {
            case IN:
                this.in(walletEntity, uro.getAmount());
                break;
            case OUT:
                this.out(walletEntity, uro.getAmount());
                break;
            case FREEZE:
                this.freeze(walletEntity, uro.getAmount());
                break;
            case THAW:
                this.thaw(walletEntity, uro.getAmount());
                break;
            case FREEZE_SETTLED:
                this.freezeSettled(walletEntity, uro.getAmount());
                break;
            default:
        }
        this.sysWalletRecordService.create(walletEntity.getId(), uro);
        return this.updateById(walletEntity);
    }

    private void in(SysWalletEntity walletEntity, BigDecimal amount) {
        walletEntity.setFlow(walletEntity.getFlow().add(amount));
        walletEntity.setTotal(walletEntity.getTotal().add(amount));
        walletEntity.setBalance(walletEntity.getBalance().add(amount));
    }

    private void out(SysWalletEntity walletEntity, BigDecimal amount) {
        ApiAssert.state(NumberUtil.isGreaterOrEqual(walletEntity.getBalance(), amount), WalletCode.Message.BALANCE_NOT_ENOUGH);
        walletEntity.setTotal(walletEntity.getTotal().subtract(amount));
        walletEntity.setBalance(walletEntity.getBalance().subtract(amount));
    }

    private void freeze(SysWalletEntity walletEntity, BigDecimal amount) {
        ApiAssert.state(NumberUtil.isGreaterOrEqual(walletEntity.getBalance(), amount), WalletCode.Message.BALANCE_NOT_ENOUGH);
        walletEntity.setBalance(walletEntity.getBalance().subtract(amount));
        walletEntity.setFrozen(walletEntity.getFrozen().add(amount));
    }

    private void thaw(SysWalletEntity walletEntity, BigDecimal amount) {
        walletEntity.setBalance(walletEntity.getBalance().add(amount));
        walletEntity.setFrozen(walletEntity.getFrozen().subtract(amount));
    }

    private void freezeSettled(SysWalletEntity walletEntity, BigDecimal amount) {
        walletEntity.setTotal(walletEntity.getTotal().subtract(amount));
        walletEntity.setFrozen(walletEntity.getFrozen().subtract(amount));
    }

    @Override
    public SysWalletEntity getByUid(Long uid) {
        return this.getOne(Wrappers.<SysWalletEntity>lambdaQuery().eq(SysWalletEntity::getUid, uid));
    }

}