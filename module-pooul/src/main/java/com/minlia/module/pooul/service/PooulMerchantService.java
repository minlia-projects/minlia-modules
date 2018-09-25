package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.qo.PooulMerchatQO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;

/**
 * 普尔商户管理接口
 * Created by garen on 2018/9/5.
 */
public interface PooulMerchantService {

    String create_url = "/cms/merchants";

    String delete_url = "/cms/merchants/{merchantId}";

    Response create(String guid, PooulMerchantPersonalCTO cto);

    Response create(String guid, PooulMerchantCTO cto);

    Response update();

    Response delete(String merchantId);

    /**
     * 查询
     * @param qo
     */
    Response queryOne(PooulMerchatQO qo);

    /**
     * 搜索
     * @param qo
     */
    Response queryPage(PooulMerchatQO qo);

}
