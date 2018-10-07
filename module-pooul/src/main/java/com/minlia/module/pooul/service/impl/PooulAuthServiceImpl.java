package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulLoginTO;
import com.minlia.module.pooul.config.PooulProperties;
import com.minlia.module.pooul.contract.PooulCode;
import com.minlia.module.pooul.service.PooulAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by garen on 2018/9/5.
 */
@Service
public class PooulAuthServiceImpl implements PooulAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PooulProperties pooulProperties;

    @Override
    public String login() {
        PooulLoginTO loginTO = new PooulLoginTO(pooulProperties.getUsername(), pooulProperties.getPassword());
        ResponseEntity<PooulDTO> responseEntity = restTemplate.postForEntity(pooulProperties.getHost() +  "/web/user/session/login_name",loginTO,PooulDTO.class);
        ApiAssert.state(responseEntity.getBody().isSuccess(), PooulCode.Message.LOGIN_FAILURE, responseEntity.getBody().getCode(), responseEntity.getBody().getMsg());
        return responseEntity.getHeaders().get("authorization").get(0);
    }

    @Override
    public HttpHeaders getHeaders(){
        //获取token
        String authorization = this.login();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",authorization);
        return headers;
    }

}
