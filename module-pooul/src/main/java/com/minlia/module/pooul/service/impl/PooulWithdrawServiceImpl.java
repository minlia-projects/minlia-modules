package com.minlia.module.pooul.service.impl;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.pooul.bean.dto.PooulDTO;
import com.minlia.module.pooul.bean.dto.PooulWithdrawQueryDTO;
import com.minlia.module.pooul.bean.qo.PooulCmbaYqQO;
import com.minlia.module.pooul.bean.to.PooulWithdrawTO;
import com.minlia.module.pooul.config.PooulProperties;
import com.minlia.module.pooul.contract.PooulCode;
import com.minlia.module.pooul.service.PooulAuthService;
import com.minlia.module.pooul.service.PooulWithdrawService;
import com.minlia.module.pooul.util.PooulToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by garen on 2018/9/25.
 */
@Slf4j
@Service
public class PooulWithdrawServiceImpl implements PooulWithdrawService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PooulProperties pooulProperties;

    @Autowired
    private PooulAuthService pooulAuthService;

    @Override
    public Response withdraw(String merchantId, PooulWithdrawTO pooulWithdrawTO) {
        Map map = new BeanMap(pooulWithdrawTO);
        String token = PooulToken.create(map);
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(pooulProperties.getHost() + POST_WITHDRAW_URL + merchantId).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul创建订单失败:", e);
            ApiAssert.state(false, PooulCode.Message.ORDER_CREATE_FAILURE, e.getMessage());
        }
        PooulDTO pooulDTO = new Gson().fromJson(response.getBody(),PooulDTO.class);
        return Response.is(pooulDTO.isSuccess(), pooulDTO.getCode(), pooulDTO.getMsg());
    }

    @Override
    public Response query(String merchantId, String mch_withdraw_id) {
        Map map = Maps.newHashMap();
        map.put("mch_withdraw_id",mch_withdraw_id);
        String token = PooulToken.create(map);
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(pooulProperties.getHost() + GET_WITHDRAW_URL + merchantId).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul对外转账状态查询:", e);
            ApiAssert.state(false, PooulCode.Message.ORDER_CREATE_FAILURE, e.getMessage());
        }
        PooulWithdrawQueryDTO queryDTO = new Gson().fromJson(response.getBody(),PooulWithdrawQueryDTO.class);
        return Response.success(queryDTO.getCode(), queryDTO.getMsg(), queryDTO.getData());
    }

    @Override
    public Response fundAccount(PooulCmbaYqQO qo) {
        HttpEntity httpEntity = new HttpEntity(pooulAuthService.getHeaders());
        ResponseEntity<Map> responseEntity = restTemplate.exchange(pooulProperties.getHost() + FUND_ACCOUNT_URL, HttpMethod.GET, httpEntity, Map.class, qo);
        return Response.success(responseEntity.getBody());
    }

}
