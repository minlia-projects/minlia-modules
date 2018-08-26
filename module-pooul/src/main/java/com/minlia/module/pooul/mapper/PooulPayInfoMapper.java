package com.minlia.module.pooul.mapper;

import com.minlia.module.pooul.bean.domain.PooulPayInfoDO;

/**
 * Created by garen on 2018/8/26.
 */
public interface PooulPayInfoMapper {

    int create(PooulPayInfoDO pooulPayInfoDO);

    int update(PooulPayInfoDO pooulPayInfoDO);

    PooulPayInfoDO queryOne(String mchTradeId);

}
