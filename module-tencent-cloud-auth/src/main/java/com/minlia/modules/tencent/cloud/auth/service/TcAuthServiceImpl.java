package com.minlia.modules.tencent.cloud.auth.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.tencent.cloud.auth.bean.FaceAuth;
import com.minlia.modules.tencent.cloud.auth.body.*;
import com.minlia.modules.tencent.cloud.auth.body.response.TcFaceIdResultResponseBody;
import com.minlia.modules.tencent.cloud.auth.config.TcAuthConfig;
import com.minlia.modules.tencent.cloud.auth.entity.FaceIdRecord;
import com.minlia.modules.tencent.cloud.auth.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private FaceIdRecordService faceIdRecordService;

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
    public String getApiSignTicket() {
        return this.getApiSignTicket(false);
    }

    @Override
    public String getApiSignTicket(boolean forceRefresh) {
        Lock lock = this.authConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.authConfig.expireApiSignTicket();
            }
            if(this.authConfig.isApiSignTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.authConfig.getApiSignTicketUrl(), authConfig.getAppid(), this.getAccessToken());
                TcApiTicketResponseBody responseBody = restTemplate.getForObject(url, TcApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    TcApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.authConfig.updateApiSignTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取Api Ticket失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.authConfig.getApiSignTicket().getValue();
    }

    @Override
    public String getApiNonceTicket() {
        return getApiNonceTicket(false);
    }

    @Override
    public String getApiNonceTicket(boolean forceRefresh) {
        Lock lock = this.authConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.authConfig.expireApiNonceTicket();
            }
            if(this.authConfig.isApiNonceTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.authConfig.getApiNonceTicketUrl(), authConfig.getAppid(), this.getAccessToken(), SecurityContextHolder.getCurrentGuid());
                TcApiTicketResponseBody responseBody = restTemplate.getForObject(url, TcApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    TcApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.authConfig.updateApiNonceTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取Api Ticket失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.authConfig.getApiNonceTicket().getValue();
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
        values.add(this.getApiSignTicket());
        return SignUtils.sign(values);
    }

    @Override
    public StatefulBody geth5faceid(TcFaceIdRequestBody requestBody) {
        String userId = SecurityContextHolder.getCurrentGuid();
        FaceIdRecord faceIdRecord = faceIdRecordService.queryOne(TcFaceIdRecordQueryRequestBody.builder().userId(userId).build());
        ApiPreconditions.is(null != faceIdRecord && faceIdRecord.getIsAuth(),ApiCode.NOT_AUTHORIZED,"已认证");
        String orderNo = NumberGenerator.generatorByTimestamp("ON",3);

        FaceAuth faceAuth = FaceAuth.builder()
                .webankAppId(this.authConfig.getAppid())
                .orderNo(orderNo)
                .name(requestBody.getName())
                .idNo(requestBody.getIdNo())
                .userId(userId)
                .sourcePhotoType("1")
                .version("1.0.0")
                .sign(this.sign(orderNo,requestBody.getName(),requestBody.getIdNo(),userId))
                .build();
        ResponseEntity<TcFaceIdResponseBody> responseEntity = restTemplate.postForEntity("https://idasc.webank.com/api/server/h5/geth5faceid",faceAuth,TcFaceIdResponseBody.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (responseEntity.getBody().isSuccess()) {
                TcFaceIdResult result = responseEntity.getBody().getResult();

                String nonce = UUID.randomUUID().toString();
                nonce = nonce.replace("-","");
                //设置签名
                List<String> values = Lists.newArrayList();
                values.add(this.authConfig.getAppid());
                values.add(orderNo);
                values.add(userId);
                values.add("1.0.0");
                values.add(result.getH5faceId());
                values.add(getApiNonceTicket());
                values.add(nonce);
                result.setSign(SignUtils.sign(values));

//                result.setAppId(FACEID_APP_ID);
                result.setWebankAppId(this.authConfig.getAppid());
                result.setVersion("1.0.0");
                result.setNonce(nonce);
                result.setResultType(null);
                result.setUserId(userId);

                //持久化
//                if (null == faceIdRecord) {
                    faceIdRecord = FaceIdRecord.builder()
                            .orderNo(orderNo)
                            .userId(userId)
                            .name(requestBody.getName())
                            .idNo(requestBody.getIdNo())
                            .isAuth(false)
                            .build();
                    faceIdRecordService.create(faceIdRecord);
//                }
                return SuccessResponseBody.builder().payload(result).build();
            } else {
                return FailureResponseBody.builder().code(Integer.valueOf(responseEntity.getBody().getCode())).message(responseEntity.getBody().getMsg()).build();
            }
        } else {
            return FailureResponseBody.builder().code(responseEntity.getStatusCode().value()).build();
        }
    }

    @Override
    public TcFaceIdResultResponseBody getH5faceidResult(String orderNo) {
        String nonce = UUID.randomUUID().toString().replace("-", "");
        List<String> values = Lists.newArrayList();
        values.add(this.authConfig.getAppid());
        values.add(orderNo);
        values.add(this.getApiSignTicket());
        values.add("1.0.0");
        values.add(nonce);
        String sign = SignUtils.sign(values);

        Map<String,String> map = Maps.newHashMap();
        map.put("app_id",this.authConfig.getAppid());
        map.put("version","1.0.0");
        map.put("nonce",nonce);
        map.put("order_no",orderNo);
        map.put("sign",sign);
        TcFaceIdResultResponseBody responseBody = restTemplate.getForObject(authConfig.getH5faceidResultUrl(), TcFaceIdResultResponseBody.class,map);
        return responseBody;
    }

}
