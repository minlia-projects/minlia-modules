package com.minlia.module.wallet.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.wallet.bean.WalletTransferRo;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.constant.WalletCode;
import com.minlia.module.wallet.entity.SysWalletEntity;
import com.minlia.module.wallet.entity.SysWalletTransferEntity;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import com.minlia.module.wallet.mapper.SysWalletTransferMapper;
import com.minlia.module.wallet.service.SysWalletService;
import com.minlia.module.wallet.service.SysWalletTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 钱包-转账 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-12
 */
@Service
@RequiredArgsConstructor
public class SysWalletTransferServiceImpl extends ServiceImpl<SysWalletTransferMapper, SysWalletTransferEntity> implements SysWalletTransferService {

    private final SysWalletService sysWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(WalletTransferRo transferRo) {
        SysWalletEntity walletEntity = sysWalletService.getByUid(transferRo.getFrom());
        ApiAssert.state(NumberUtil.isGreaterOrEqual(walletEntity.getBalance(), transferRo.getAmount()), WalletCode.Message.BALANCE_NOT_ENOUGH);

        sysWalletService.update(WalletUro.builder().uid(transferRo.getFrom()).businessId(transferRo.getBusinessId()).type(WalletOperationTypeEnum.OUT).amount(transferRo.getAmount()).remark(transferRo.getRemark()).build());
        sysWalletService.update(WalletUro.builder().uid(transferRo.getTo()).businessId(transferRo.getBusinessId()).type(WalletOperationTypeEnum.IN).amount(transferRo.getAmount()).remark(transferRo.getRemark()).build());

        SysWalletTransferEntity transferEntity = DozerUtils.map(transferRo, SysWalletTransferEntity.class);
        return this.save(transferEntity);
    }

}