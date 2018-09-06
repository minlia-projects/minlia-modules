package com.minlia.module.pooul.service;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulMerchantDO;
import com.minlia.module.pooul.bean.qo.PooulMerchatInternalQO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 普尔商户管理接口
 * Created by garen on 2018/9/5.
 */
public interface PooulMerchantInternalService {

    PooulMerchantDO create(PooulMerchantDO merchantDO);

    PooulMerchantDO update(PooulMerchantDO merchantDO);

    Response delete(PooulMerchantDO merchantDO);

    boolean exists(PooulMerchatInternalQO qo);

    PooulMerchantDO queryOne(PooulMerchatInternalQO qo);

    List<PooulMerchantDO> queryList(PooulMerchatInternalQO qo);

    PageInfo<PooulMerchantDO> queryPage(PooulMerchatInternalQO qo, Pageable pageable);

}
