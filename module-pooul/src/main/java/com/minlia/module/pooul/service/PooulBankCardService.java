package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;

/**
 * Created by garen on 2018/9/21.
 */
public interface PooulBankCardService {

    /**
     * 创建
     * @param merchantId
     * @param cto
     * @return
     */
    Response create(String merchantId, PooulBankCardCTO cto);

    Response delete(String merchantId, Long recordId);

    Response setDefaultUrl(String merchantId, Long recordId);

    Response queryOne(String merchantId, Long recordId);

    Response queryAll(String merchantId);

}
