package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulBankCardDO;
import com.minlia.module.pooul.bean.dto.PooulBankcardCreateDTO;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulBankCardCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.mapper.PooulBankcardMapper;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulBankCardService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by garen on 2018/9/21.
 */
@Service
public class PooulBankCardServiceImpl implements PooulBankCardService {

    @Value("${pooul.host}")
    public String pooulHost;

    @Autowired
    private Mapper mapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PooulAuthService pooulAuthService;
    @Autowired
    private PooulBankcardMapper pooulBankcardMapper;

    @Override
    public Response create(String merchantId, PooulBankCardCTO cto) {
        HttpEntity<PooulMerchantCTO> httpEntity = new HttpEntity(cto, pooulAuthService.getHeaders());
        PooulBankcardCreateDTO dto = restTemplate.postForObject(pooulHost + create_url, httpEntity, PooulBankcardCreateDTO.class, merchantId);
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
        ResponseEntity<PooulDTO> responseEntity = restTemplate.exchange(pooulHost + delete_url, HttpMethod.DELETE, httpEntity, PooulDTO.class, recordId, merchantId);
        if (responseEntity.getBody().isSuccess()) {
            pooulBankcardMapper.delete(recordId);
        }
        return Response.is(responseEntity.getBody().isSuccess(), responseEntity.getBody().getCode(), responseEntity.getBody().getMsg());
    }

    @Override
    public Response setDefaultUrl(String merchantId, Long recordId) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        ResponseEntity<PooulDTO> responseEntity = restTemplate.exchange(pooulHost + set_default_url, HttpMethod.PUT, httpEntity, PooulDTO.class, recordId, merchantId);
        return Response.is(responseEntity.getBody().isSuccess(), responseEntity.getBody().getCode(), responseEntity.getBody().getMsg());
    }

    @Override
    public Response queryOne(String merchantId, Long recordId) {
        return null;
    }

    @Override
    public Response queryAll(String merchantId) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        ResponseEntity<Map> responseEntity = restTemplate.exchange(pooulHost + query_all_url, HttpMethod.GET, httpEntity, Map.class, merchantId);
        return Response.success(responseEntity.getBody());
    }

}
