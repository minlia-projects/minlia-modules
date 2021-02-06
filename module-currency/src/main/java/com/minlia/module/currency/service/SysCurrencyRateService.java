package com.minlia.module.currency.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.currency.entity.SysCurrencyRateEntity;

import java.math.BigDecimal;

/**
 * <p>
 * 货币汇率 服务类
 * </p>
 *
 * @author garen
 * @since 2021-01-08
 */
public interface SysCurrencyRateService extends IService<SysCurrencyRateEntity> {

    /**
     * 根据货币对获取汇率
     *
     * @param symbol
     * @return
     */
    BigDecimal getRate(String symbol);

    /**
     * 根据货币对获取汇率
     *
     * @param curBase
     * @param curTrans
     * @return
     */
    BigDecimal getRate(String curBase, String curTrans);

    /**
     * 换算
     *
     * @param amount
     * @param curBase
     * @param curTrans
     * @return
     */
    BigDecimal convert(BigDecimal amount, String curBase, String curTrans);

}