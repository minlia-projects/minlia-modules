package com.minlia.module.currency.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.currency.entity.SysCurrencyRateEntity;
import com.minlia.module.currency.mapper.SysCurrencyRateMapper;
import com.minlia.module.currency.service.SysCurrencyRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 货币汇率 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
@Service
public class SysCurrencyRateServiceImpl extends ServiceImpl<SysCurrencyRateMapper, SysCurrencyRateEntity> implements SysCurrencyRateService {

    @Override
    public BigDecimal getRate(String symbol) {
        SysCurrencyRateEntity entity = this.getOne(Wrappers.<SysCurrencyRateEntity>lambdaQuery().eq(SysCurrencyRateEntity::getSymbol, symbol));
        return entity.getRate();
    }

    @Override
    public BigDecimal getRate(String curBase, String curTrade) {
        if (curBase.equals(curBase)) {
            return BigDecimal.ONE;
        }
        SysCurrencyRateEntity entity = this.getOne(Wrappers.<SysCurrencyRateEntity>lambdaQuery().eq(SysCurrencyRateEntity::getCurBase, curBase).eq(SysCurrencyRateEntity::getCurTrans, curTrade));
        return entity.getRate();
    }

}