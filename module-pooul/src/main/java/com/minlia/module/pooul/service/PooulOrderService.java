package com.minlia.module.pooul.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/7/18.
 */
public interface PooulOrderService {

    void create(PooulOrderDO pooulOrderDO);

    void update(PooulOrderDO pooulOrderDO);

    PooulOrderDO queryByMchTradeId(String mchTradeId);

    PooulOrderDO one(PooulOrderQO qo);

    List<PooulOrderDO> list(PooulOrderQO qo);

    PageInfo<PooulOrderDO> page(PooulOrderQO qo, Pageable pageable);

}
