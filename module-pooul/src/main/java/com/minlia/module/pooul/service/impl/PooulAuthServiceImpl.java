package com.minlia.module.pooul.service.impl;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.to.PooulLoginTO;
import com.minlia.module.pooul.service.PooulAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Value("${pooul.auth.username}")
    public String username;

    @Value("${pooul.auth.password}")
    public String password;



    @Override
    public String login() {
        PooulLoginTO loginTO = new PooulLoginTO(username,password);
        ResponseEntity<PooulDTO> responseEntity = restTemplate.postForEntity("https://api-dev.pooul.com/web/user/session/login_name",loginTO,PooulDTO.class);
        ApiPreconditions.not(responseEntity.getStatusCode().equals(HttpStatus.OK), ApiCode.BASED_ON, "登录支付平台失败:" + responseEntity.getStatusCodeValue());
        ApiPreconditions.not(responseEntity.getBody().isSuccess(), ApiCode.BASED_ON, "登录支付平台失败:" + responseEntity.getBody().getMsg());
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
