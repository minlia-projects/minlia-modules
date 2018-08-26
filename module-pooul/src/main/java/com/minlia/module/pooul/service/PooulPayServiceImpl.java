package com.minlia.module.pooul.service;

import com.auth0.jwt.interfaces.Claim;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.domain.PooulPayInfoDO;
import com.minlia.module.pooul.bean.dto.PooulPayData;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;
import com.minlia.module.pooul.config.PooulProperties;
import com.minlia.module.pooul.contract.PooulContracts;
import com.minlia.module.pooul.enumeration.PayStatusEnum;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import com.minlia.module.pooul.mapper.PooulOrderMapper;
import com.minlia.module.pooul.mapper.PooulPayInfoMapper;
import com.minlia.module.pooul.util.PooulToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by garen on 2018/7/18.
 */
@Slf4j
@Service
public class PooulPayServiceImpl implements PooulPayService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private PooulProperties pooulProperties;

    @Autowired
    private PooulOrderMapper pooulOrderMapper;

    @Autowired
    private PooulPayInfoMapper pooulPayInfoMapper;

    @Override
    @Transactional
    public StatefulBody wechatJsminipg(PooulWechatJsminipgTO jsminipgTO) {
        //判断订单是否已存在
        PooulOrderDO pooulOrderDO = pooulOrderMapper.queryOne(PooulOrderQO.builder().mchTradeId(jsminipgTO.getMchTradeId()).build());
        if (null != pooulOrderDO) {
            return SuccessResponseBody.builder().payload(pooulPayInfoMapper.queryOne(jsminipgTO.getMchTradeId())).build();
        }

        jsminipgTO.setPayType(PayTypeEnum.wechat_jsminipg.getName());
        jsminipgTO.setNonceStr(NumberGenerator.uuid32());
        if (StringUtils.isBlank(jsminipgTO.getNotifyUrl())) {
            jsminipgTO.setNotifyUrl(pooulProperties.getNotifyUrl());
        }

        Map map = new BeanMap(jsminipgTO);
        String token = PooulToken.create(map);

        HttpResponse<String> response = null;
        try {
            response = Unirest.post(pooulProperties.getUrlV2Pay()).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul创建订单失败:", e);
            ApiPreconditions.is(true, ApiCode.BASED_ON, "Pooul创建订单失败:" + e.getMessage());
        }

        //如果已"{"开始表明创建订单失败，返回错误信息
        if (response.getBody().startsWith(PooulContracts.RIGHT_PARENTHESIS)) {
            JSONObject jsonObject = JSONObject.fromObject(response.getBody());
            return FailureResponseBody.builder().code((Integer) jsonObject.get(PooulContracts.CODE)).message((String) jsonObject.get(PooulContracts.MSG)).build();
        }

        //获取返回token
        Map<String, Claim> claims = PooulToken.getClaims(response.getBody());
        if (!claims.get(PooulContracts.CODE).asInt().equals(NumberUtils.INTEGER_ZERO)) {
            ApiPreconditions.is(true, ApiCode.BASED_ON, claims.get(PooulContracts.MSG).asString());
        }

        //保存订单请求信息
        pooulOrderDO = mapper.map(jsminipgTO, PooulOrderDO.class);
        pooulOrderDO.setPayStatus(PayStatusEnum.UNPAID);
        pooulOrderMapper.create(pooulOrderDO);

        //保存支付参数
        PooulPayData pooulData = claims.get(PooulContracts.DATA).as(PooulPayData.class);
        PooulPayInfoDO pooulPayInfo = new Gson().fromJson(pooulData.getPay_info(),PooulPayInfoDO.class);
        pooulPayInfo.setMchTradeId(jsminipgTO.getMchTradeId());
        pooulPayInfoMapper.create(pooulPayInfo);
        return SuccessResponseBody.builder().payload(pooulPayInfo).build();
    }

    @Override
    public StatefulBody close(String mchTradeId) {
        PooulOrderDO pooulOrderDO = pooulOrderMapper.queryOne(PooulOrderQO.builder().mchTradeId(mchTradeId).build());
        ApiPreconditions.is(null == pooulOrderDO,ApiCode.BASED_ON,"订单不存在");

        Map<String,Object> map = Maps.newHashMap();
        map.put("mch_trade_id",mchTradeId);
        map.put("nonce_str",NumberGenerator.uuid32());
        String token = PooulToken.create(map);

        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://api-dev.pooul.com/v2/pay/close?merchant_id=2162288807443437").body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul关闭订单失败:", e);
            ApiPreconditions.is(true, ApiCode.BASED_ON, "Pooul关闭订单失败:" + e.getMessage());
        }

        //如果已"{"开始表明创建订单失败，返回错误信息
        if (response.getBody().startsWith(PooulContracts.RIGHT_PARENTHESIS)) {
            JSONObject jsonObject = JSONObject.fromObject(response.getBody());
            return FailureResponseBody.builder().code((Integer) jsonObject.get(PooulContracts.CODE)).message((String) jsonObject.get(PooulContracts.MSG)).build();
        }

        //获取返回token
        Map<String, Claim> claims = PooulToken.getClaims(response.getBody());
        if (claims.get(PooulContracts.CODE).asInt().equals(NumberUtils.INTEGER_ZERO)) {
            pooulOrderDO.setPayStatus(PayStatusEnum.CLOSED);
            pooulOrderMapper.update(pooulOrderDO);
            return SuccessResponseBody.builder().code(claims.get(PooulContracts.CODE).asInt()).message(claims.get(PooulContracts.MSG).asString()).build();
        } else {
            return FailureResponseBody.builder().code(claims.get(PooulContracts.CODE).asInt()).message(claims.get(PooulContracts.MSG).asString()).build();
        }
    }

    @Override
    public StatefulBody reverse(String mchTradeId) {
        return null;
    }

}
