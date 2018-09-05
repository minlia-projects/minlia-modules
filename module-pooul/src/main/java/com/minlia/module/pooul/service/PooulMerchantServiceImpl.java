package com.minlia.module.pooul.service;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.dto.PooulMerchantCreateDTO;
import com.minlia.module.pooul.bean.qo.PooulMerchatQO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;
import com.minlia.module.pooul.config.PooulMerchantProperties;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/9/5.
 */
@Slf4j
@Service
public class PooulMerchantServiceImpl implements PooulMerchantService{

    @Autowired
    private Mapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PooulAuthService pooulAuthService;

    @Autowired
    private PooulMerchantProperties pooulMerchantProperties;

    @Override
    public Response create(PooulMerchantPersonalCTO cto) {
        PooulMerchantCTO pooulMerchantCTO = mapper.map(cto,PooulMerchantCTO.class);
        pooulMerchantCTO.setLicense_type(3);
        pooulMerchantCTO.setArrears(false);
        return this.create(pooulMerchantCTO);
    }

    @Override
    public Response create(PooulMerchantCTO cto) {
        cto.setMerchant_type(3);
        cto.setPlatform_merchant_id(pooulMerchantProperties.getPlatformMerchantId());
        cto.setParent_id(pooulMerchantProperties.getParentId());
        HttpEntity<PooulMerchantCTO> httpEntity = new HttpEntity(cto, this.getHeaders());
        PooulMerchantCreateDTO createDTO = restTemplate.postForObject(pooulMerchantProperties.getCreateUrl(),httpEntity,PooulMerchantCreateDTO.class);
        return Response.is(createDTO.isSuccess(),createDTO.getMsg(),createDTO.getData());
    }

    @Override
    public Response update() {
        return null;
    }

    @Override
    public Response delete(String merchantId) {
        HttpEntity httpEntity = new HttpEntity(null, this.getHeaders());
        ResponseEntity<PooulDTO> responseEntity = restTemplate.exchange(pooulMerchantProperties.getDeleteUrl(), HttpMethod.DELETE, httpEntity,PooulDTO.class,merchantId);
        return Response.is(responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody().isSuccess(),responseEntity.getBody().getMsg());
    }

    @Override
    public Response queryById(Long id) {
        return null;
    }

    @Override
    public Response page(PooulMerchatQO qo, Pageable pageable) {
        return null;
    }

    private HttpHeaders getHeaders(){
        //获取token
        String authorization = pooulAuthService.login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",authorization);
        return headers;
    }

}
