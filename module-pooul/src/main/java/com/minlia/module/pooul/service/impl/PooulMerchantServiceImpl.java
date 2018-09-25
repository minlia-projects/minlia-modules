package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.pooul.bean.domain.PooulMerchantDO;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.dto.PooulMerchantCreateDTO;
import com.minlia.module.pooul.bean.qo.PooulMerchatInternalQO;
import com.minlia.module.pooul.bean.qo.PooulMerchatQO;
import com.minlia.module.pooul.bean.to.PooulMerchantCTO;
import com.minlia.module.pooul.bean.to.PooulMerchantPersonalCTO;
import com.minlia.module.pooul.config.PooulMerchantProperties;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulMerchantInternalService;
import com.minlia.module.pooul.service.PooulMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/9/5.
 */
@Slf4j
@Service
public class PooulMerchantServiceImpl implements PooulMerchantService {


    @Value("${pooul.host}")
    public String pooulHost;

    @Autowired
    private Mapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PooulAuthService pooulAuthService;

    @Autowired
    private PooulMerchantProperties pooulMerchantProperties;

    @Autowired
    private PooulMerchantInternalService pooulMerchantInternalService;

    @Override
    public Response create(String guid, PooulMerchantPersonalCTO cto) {
        PooulMerchantCTO pooulMerchantCTO = mapper.map(cto,PooulMerchantCTO.class);
        pooulMerchantCTO.setLicense_type(3);
        pooulMerchantCTO.setArrears(false);
        return this.create(guid, pooulMerchantCTO);
    }

    @Override
    public Response create(String guid, PooulMerchantCTO cto) {
        //判断是否已存在
        boolean exists = pooulMerchantInternalService.exists(PooulMerchatInternalQO.builder().guid(guid).build());
        ApiAssert.state(!exists, SystemCode.Message.DATA_ALREADY_EXISTS);

        cto.setMerchant_type(3);
        cto.setPlatform_merchant_id(pooulMerchantProperties.getPlatformMerchantId());
        cto.setParent_id(pooulMerchantProperties.getParentId());
        HttpEntity<PooulMerchantCTO> httpEntity = new HttpEntity(cto, pooulAuthService.getHeaders());
//        Object createDTO1 = restTemplate.postForObject(pooulHost + create_url,httpEntity,Object.class);
        PooulMerchantCreateDTO createDTO = restTemplate.postForObject(pooulHost + create_url,httpEntity,PooulMerchantCreateDTO.class);

        if (createDTO.isSuccess()) {
            pooulMerchantInternalService.create(PooulMerchantDO.builder()
                    .guid(guid)
                    .platformMerchantId(pooulMerchantProperties.getPlatformMerchantId())
                    .parentId(pooulMerchantProperties.getParentId())
                    .number(createDTO.getData().get_id())
                    .name(cto.getBusiness().getShort_name())
                    .build()
            );
        }
        return Response.is(createDTO.isSuccess(), createDTO.getCode(), createDTO.getMsg(),createDTO.getData());
    }

    @Override
    public Response update() {
        return null;
    }

    @Override
    public Response delete(String merchantId) {
        //判断是否已存在
//        boolean exists = pooulMerchantInternalService.exists(PooulMerchatInternalQO.builder().number(merchantId).build());
//        ApiAssert.state(exists, SystemCode.Message.DATA_NOT_EXISTS);

        HttpEntity httpEntity = new HttpEntity(null, pooulAuthService.getHeaders());
        ResponseEntity<PooulDTO> responseEntity = restTemplate.exchange(pooulHost + delete_url, HttpMethod.DELETE, httpEntity,PooulDTO.class,merchantId);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody().isSuccess()) {
            pooulMerchantInternalService.delete(PooulMerchantDO.builder().number(merchantId).build());
            return Response.success(responseEntity.getBody().getMsg());
        } else {
            return Response.failure(responseEntity.getBody().getMsg());
        }
    }

    @Override
    public Response queryOne(PooulMerchatQO qo) {
        return null;
    }

    @Override
    public Response queryPage(PooulMerchatQO qo) {
        return null;
    }

}
