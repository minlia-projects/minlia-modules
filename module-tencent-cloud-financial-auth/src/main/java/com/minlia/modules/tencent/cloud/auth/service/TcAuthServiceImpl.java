package com.minlia.modules.tencent.cloud.auth.service;

import com.google.common.collect.Lists;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.tencent.cloud.auth.bean.FaceAuth;
import com.minlia.modules.tencent.cloud.auth.body.*;
import com.minlia.modules.tencent.cloud.auth.config.TcAuthConfig;
import com.minlia.modules.tencent.cloud.auth.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

/**
 * Created by garen on 2018/4/19.
 */
@Slf4j
@Service
public class TcAuthServiceImpl implements TcAuthService{

    @Autowired
    private RestTemplate restTemplate;

    private TcAuthConfig authConfig;

    @Override
    public String getAccessToken() {
        return this.getAccessToken(false);
    }

    @Override
    public String getAccessToken(boolean forceRefresh) {
        Lock lock = this.authConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.authConfig.expireAccessToken();
            }
            if(this.authConfig.isAccessTokenExpired()) {
                //如果失效重新获取
                String url = this.authConfig.getAccessTokenUrl();
                TcAccessTokenResponseBody responseBody = restTemplate.getForObject(url, TcAccessTokenResponseBody.class);
                if (responseBody.isSuccess()) {
                    this.authConfig.updateAccessToken(responseBody.getAccessToken(),responseBody.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取AccessToken失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.authConfig.getAccessToken().getValue();
    }

    @Override
    public String getApiTicket() {
        return this.getApiTicket(false);
    }

    @Override
    public String getApiTicket(boolean forceRefresh) {
        Lock lock = this.authConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.authConfig.expireApiTicket();
            }
            if(this.authConfig.isApiTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.authConfig.getApiTicketUrl(),authConfig.getAppid(),this.getAccessToken());
                TcApiTicketResponseBody responseBody = restTemplate.getForObject(url, TcApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    TcApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.authConfig.updateApiTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取Api Ticket失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.authConfig.getApiTicket().getValue();
    }

    @Override
    public TcAuthConfig getAuthConfig() {
        return this.authConfig;
    }

    @Override
    public TcAuthConfig setTcAuthConfig(TcAuthConfig authConfig) {
        return this.authConfig = authConfig;
    }

    @Override
    public String sign(String orderNo, String name, String idNo, String userId) {
        List<String> values = Lists.newArrayList();
        values.add(this.authConfig.getAppid());
        values.add(orderNo);
        values.add(name);
        values.add(idNo);
        values.add(userId);
        values.add("1.0.0");
        values.add(this.getApiTicket());
        return SignUtils.sign(values);
    }

    @Override
    public StatefulBody geth5faceid(TcFaceIdRequestBody requestBody) {
        FaceAuth faceAuth = FaceAuth.builder()
                .webankAppId(this.authConfig.getAppid())
                .orderNo(requestBody.getOrderNo())
                .name(requestBody.getName())
                .idNo(requestBody.getIdNo())
                .userId(requestBody.getUserId())
                .sourcePhotoType("1")
                .version("1.0.0")
                .sign(this.sign(requestBody.getOrderNo(),requestBody.getName(),requestBody.getIdNo(),requestBody.getUserId()))
                .build();
        ResponseEntity<TcFaceIdResponseBody> responseEntity = restTemplate.postForEntity("https://idasc.webank.com/api/server/h5/geth5faceid",faceAuth,TcFaceIdResponseBody.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (responseEntity.getBody().isSuccess()) {
                TcFaceIdResult result = responseEntity.getBody().getResult();
                //设置签名 TODO
                List<String> values = Lists.newArrayList();
                values.add(this.authConfig.getAppid());
                values.add(requestBody.getOrderNo());
                values.add(requestBody.getUserId());
                values.add("1.0.0");
                values.add(result.getH5faceId());
                values.add(getApiTicket());
                values.add(UUID.randomUUID().toString());
                result.setSign(SignUtils.sign(values));
                result.setAppId(this.authConfig.getAppid());
                result.setWebankAppId(this.authConfig.getAppid());
                result.setVersion("1.0.0");
                result.setNonce(UUID.randomUUID().toString());
                result.setUserId(requestBody.getUserId());
                return SuccessResponseBody.builder().payload(result).build();
            } else {
                return FailureResponseBody.builder().code(Integer.valueOf(responseEntity.getBody().getCode())).message(responseEntity.getBody().getMsg()).build();
            }
        } else {
            return  FailureResponseBody.builder().code(responseEntity.getStatusCode().value()).build();
        }
    }

}
