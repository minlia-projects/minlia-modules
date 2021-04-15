package com.minlia.hsjs.integral.service;

import com.minlia.hsjs.integral.bean.HsjsIntegralMinusData;
import com.minlia.hsjs.integral.bean.HsjsIntegralPlusData;
import com.minlia.hsjs.integral.entity.HsjsIntegralRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分-记录 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface HsjsIntegralRecordService extends IService<HsjsIntegralRecordEntity> {

    void plus(HsjsIntegralPlusData data);

    void minus(HsjsIntegralMinusData data);

}