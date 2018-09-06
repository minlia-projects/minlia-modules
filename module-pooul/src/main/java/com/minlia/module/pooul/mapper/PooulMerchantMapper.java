package com.minlia.module.pooul.mapper;

import com.minlia.module.pooul.bean.domain.PooulMerchantDO;
import com.minlia.module.pooul.bean.qo.PooulMerchatInternalQO;

import java.util.List;

/**
 * Created by garen on 2018/9/6.
 */
public interface PooulMerchantMapper {

    int create(PooulMerchantDO merchantDO);

    int update(PooulMerchantDO merchantDO);

    int delete(PooulMerchantDO merchantDO);

    long count(PooulMerchatInternalQO qo);

    PooulMerchantDO queryOne(PooulMerchatInternalQO qo);

    List<PooulMerchantDO> queryList(PooulMerchatInternalQO qo);

}
