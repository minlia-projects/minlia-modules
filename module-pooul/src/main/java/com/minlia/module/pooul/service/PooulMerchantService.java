package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.qo.PooulMerchatQO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;
import org.springframework.data.domain.Pageable;

/**
 * 普尔商户管理接口
 * Created by garen on 2018/9/5.
 */
public interface PooulMerchantService {

    Response create(PooulMerchantPersonalCTO cto);

    Response create(PooulMerchantCTO cto);

    Response update();

    Response delete(String merchantId);

    /**
     * 查询
     * @param id
     */
    Response queryById(Long id);

    /**
     * 搜索
     * @param qo
     * @param pageable
     */
    Response page(PooulMerchatQO qo, Pageable pageable);

}
