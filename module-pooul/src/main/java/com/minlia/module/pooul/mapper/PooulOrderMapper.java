package com.minlia.module.pooul.mapper;

import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;

import java.util.List;

/**
 * Created by garen on 2018/8/26.
 */
public interface PooulOrderMapper {

    int create(PooulOrderDO pooulOrderDO);

    int update(PooulOrderDO pooulOrderDO);

    PooulOrderDO queryOne(PooulOrderQO qo);

    List<PooulOrderDO> queryList(PooulOrderQO qo);

}
