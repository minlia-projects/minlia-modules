package com.minlia.member.integral.service;

import com.minlia.member.integral.bean.IntegralMinusData;
import com.minlia.member.integral.bean.IntegralPlusData;
import com.minlia.member.integral.entity.IntegralRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分-记录 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface IntegralRecordService extends IService<IntegralRecordEntity> {

    void plus(IntegralPlusData data);

    void minus(IntegralMinusData data);

}