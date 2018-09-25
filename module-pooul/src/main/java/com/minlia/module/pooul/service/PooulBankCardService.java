package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;

/**
 * Created by garen on 2018/9/21.
 */
public interface PooulBankCardService {

    String create_url = "/cms/bank_cards?merchnat_id={merchantId}";

    String update_url = "/cms/bank_cards/{recordId}?merchnat_id={merchantId}";

    String delete_url = "/cms/bank_cards/{recordId}?merchnat_id={merchantId}";

    String query_id_url = "/cms/bank_cards/{recordId}?merchnat_id={merchantId}";

    String query_all_url = "/cms/bank_cards?merchnat_id={merchantId}";

    String set_default_url = "/cms/bank_cards/{recordId}/default_card?merchnat_id={merchantId}";

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
