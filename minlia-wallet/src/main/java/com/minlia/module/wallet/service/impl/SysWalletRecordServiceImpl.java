package com.minlia.module.wallet.service.impl;

import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.entity.SysWalletRecordEntity;
import com.minlia.module.wallet.mapper.SysWalletRecordMapper;
import com.minlia.module.wallet.service.SysWalletRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 钱包-记录 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Service
public class SysWalletRecordServiceImpl extends ServiceImpl<SysWalletRecordMapper, SysWalletRecordEntity> implements SysWalletRecordService {

    @Override
    public void create(Long walletId, WalletUro uro) {
        this.save(SysWalletRecordEntity.builder()
                .uid(uro.getUid())
                .walletId(walletId)
                .type(uro.getType())
                .amount(uro.getAmount())
                .businessType(uro.getBusinessType())
                .businessId(uro.getBusinessId())
                .remark(uro.getRemark())
                .build());
    }

}