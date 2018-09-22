package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulBankCardDO;
import com.minlia.module.pooul.bean.dto.PooulBankcardCreateDTO;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.config.PooulBankCardProperties;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulBankCardService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/9/21.
 */
@Service
public class PooulBankCardServiceImpl implements PooulBankCardService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PooulAuthService pooulAuthService;
    @Autowired
    private PooulBankCardProperties pooulBankCardProperties;

    @Override
    public Response create(String merchantId, PooulBankCardCTO cto) {
        HttpEntity<PooulMerchantCTO> httpEntity = new HttpEntity(cto, pooulAuthService.getHeaders());
        PooulBankcardCreateDTO dto = restTemplate.postForObject(pooulBankCardProperties.getCreateUrl(), httpEntity, PooulBankcardCreateDTO.class, merchantId);
        if (dto.isSuccess()) {
            PooulBankCardDO bankCardDO = mapper.map(cto, PooulBankCardDO.class);
            bankCardDO.setMerchant_id(merchantId);
            bankCardDO.setRecord_id(dto.getData().get_id());



            return Response.is(dto.isSuccess(), dto.getCode(), dto.getMsg(), bankCardDO);
        } else {
            return Response.failure(dto.getCode(), dto.getMsg());
        }
    }

}
