package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulBankCardDO;
import com.minlia.module.pooul.bean.dto.PooulBankcardCreateDTO;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.config.PooulBankCardProperties;
import com.minlia.module.pooul.mapper.PooulBankcardMapper;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulBankCardService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    private PooulBankcardMapper pooulBankcardMapper;
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
            pooulBankcardMapper.create(bankCardDO);
            return Response.is(dto.isSuccess(), dto.getCode(), dto.getMsg(), bankCardDO);
        } else {
            return Response.failure(dto.getCode(), dto.getMsg());
        }
    }

    @Override
    public Response delete(String merchantId, Long recordId) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        ResponseEntity<PooulDTO> responseEntity = restTemplate.exchange(pooulBankCardProperties.getDeleteUrl(), HttpMethod.DELETE, httpEntity, PooulDTO.class, recordId, merchantId);
        if (responseEntity.getBody().isSuccess()) {
            pooulBankcardMapper.delete(recordId);
        }
        return Response.is(responseEntity.getBody().isSuccess(), responseEntity.getBody().getCode(), responseEntity.getBody().getMsg());
    }

    @Override
    public Response setDefaultUrl(String merchantId, Long recordId) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        ResponseEntity<Object> responseEntity = restTemplate.exchange(pooulBankCardProperties.getDeleteUrl(), HttpMethod.PUT, httpEntity, Object.class, recordId, merchantId);
        return Response.success(responseEntity.getBody());
    }

    @Override
    public Response queryOne(String merchantId, Long recordId) {
        return null;
    }

    @Override
    public Response queryAll(String merchantId) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        Object dto = restTemplate.exchange(pooulBankCardProperties.getQueryAllUrl(), HttpMethod.GET, httpEntity, Object.class, merchantId);
        return Response.success(dto);
    }

}
