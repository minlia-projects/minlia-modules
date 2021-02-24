package com.minlia.module.account.corporate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.account.corporate.bean.CorporateAccountSro;
import com.minlia.module.account.corporate.entity.CorporateAccountEntity;
import com.minlia.module.account.corporate.mapper.CorporateAccountMapper;
import com.minlia.module.account.corporate.service.CorporateAccountService;
import com.minlia.module.dozer.util.DozerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 对公帐号 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-02-18
 */
@Service
public class CorporateAccountServiceImpl extends ServiceImpl<CorporateAccountMapper, CorporateAccountEntity> implements CorporateAccountService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CorporateAccountSro sro) {
        CorporateAccountEntity corporateAccountEntity;
        if (Objects.nonNull(sro.getId())) {
            corporateAccountEntity = this.getById(sro.getId());
            DozerUtils.map(sro, corporateAccountEntity);
        } else {
            corporateAccountEntity = DozerUtils.map(sro, CorporateAccountEntity.class);
        }
        //判断是否有默认
        if (!exists(Wrappers.<CorporateAccountEntity>lambdaQuery().eq(CorporateAccountEntity::getDefFlag, true).eq(CorporateAccountEntity::getDisFlag, false).eq(CorporateAccountEntity::getCurrency, sro.getCurrency()))) {
            corporateAccountEntity.setDefFlag(true);
        } else if (sro.getDefFlag()) {
            //设置其他为非默认
            this.update(Wrappers.<CorporateAccountEntity>lambdaUpdate().set(CorporateAccountEntity::getDefFlag, false).eq(CorporateAccountEntity::getCurrency, sro.getCurrency()));
        }
        return this.saveOrUpdate(corporateAccountEntity);
    }

    @Override
    public boolean setDefault(Long id) {
        return false;
    }

    @Override
    public boolean disable(Long id) {
        CorporateAccountEntity corporateAccountEntity = this.getById(id);
        corporateAccountEntity.setDisFlag(!corporateAccountEntity.getDisFlag());
        this.updateById(corporateAccountEntity);
        return corporateAccountEntity.getDisFlag();
    }

    @Override
    public boolean exists(LambdaQueryWrapper lambdaQueryWrapper) {
        return this.count(lambdaQueryWrapper) > 0;
    }

}