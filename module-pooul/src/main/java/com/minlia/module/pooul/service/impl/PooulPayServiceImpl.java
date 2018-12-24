package com.minlia.module.pooul.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.domain.PooulPayInfoDO;
import com.minlia.module.pooul.bean.dto.PooulPayData;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.bean.to.PooulWechatJsminipgTO;
import com.minlia.module.pooul.config.PooulPayProperties;
import com.minlia.module.pooul.contract.PooulCode;
import com.minlia.module.pooul.contract.PooulContracts;
import com.minlia.module.pooul.enumeration.PayTypeEnum;
import com.minlia.module.pooul.enumeration.TradeStateEnum;
import com.minlia.module.pooul.mapper.PooulPayInfoMapper;
import com.minlia.module.pooul.service.PooulOrderService;
import com.minlia.module.pooul.service.PooulPayService;
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
    private PooulOrderService pooulOrderService;

    @Autowired
    private PooulPayProperties pooulProperties;

    @Autowired
    private PooulPayInfoMapper pooulPayInfoMapper;

    @Override
    @Transactional
    public Response wechatJsminipg(PooulWechatJsminipgTO jsminipgTO, String merchantId) {
        //判断订单是否已存在
        PooulOrderDO pooulOrderDO = pooulOrderService.queryByMchTradeId(jsminipgTO.getMchTradeId());
        if (null != pooulOrderDO) {
            return Response.success(pooulPayInfoMapper.queryOne(jsminipgTO.getMchTradeId()));
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
            log.info("**************************{}",pooulProperties.getPayUrl());
            response = Unirest.post(pooulProperties.getPayUrl()).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul创建订单失败:", e);
            ApiAssert.state(false, PooulCode.Message.ORDER_CREATE_FAILURE, e.getMessage());
        }

        //如果已"{"开始表明创建订单失败，返回错误信息
        if (response.getBody().startsWith(PooulContracts.RIGHT_PARENTHESIS)) {
            JSONObject jsonObject = JSONObject.fromObject(response.getBody());
            return Response.failure((Integer) jsonObject.get(PooulContracts.CODE), jsonObject.get(PooulContracts.MSG).toString());
        }

        //获取返回token
        Map<String, Claim> claims = PooulToken.getClaims(response.getBody());
        ApiAssert.state(claims.get(PooulContracts.CODE).asInt().equals(NumberUtils.INTEGER_ZERO), PooulCode.Message.ORDER_CREATE_FAILURE, claims.get(PooulContracts.MSG).asString());

        //保存订单请求信息
        pooulOrderDO = mapper.map(jsminipgTO, PooulOrderDO.class);
        pooulOrderDO.setMerchantId(merchantId);
        pooulOrderDO.setPayStatus(TradeStateEnum.UNPAID);
        pooulOrderService.create(pooulOrderDO);

        //保存支付参数

//        PooulPayData pooulData = claims.get(PooulContracts.DATA).as(PooulPayData.class);

        Map pooulDataMap = claims.get(PooulContracts.DATA).asMap();
        log.info("支付返回参数：{}", pooulDataMap.toString());

        PooulPayInfoDO pooulPayInfo = new Gson().fromJson(pooulDataMap.get("pay_info").toString(), PooulPayInfoDO.class);
        pooulPayInfo.setMchTradeId(jsminipgTO.getMchTradeId());
        pooulPayInfoMapper.create(pooulPayInfo);
        return Response.success(pooulPayInfo);
    }

    @Override
    public Response query(String mchTradeId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("mch_trade_id",mchTradeId);
        String token = PooulToken.create(map);
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(pooulProperties.getQueryOrderUrl()).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul查询订单失败:", e);
            ApiAssert.state(false, PooulCode.Message.ORDER_QUERY_FAILURE, e.getMessage());
        }

        //如果已"{"开始表明创建订单失败，返回错误信息
        if (response.getBody().startsWith(PooulContracts.RIGHT_PARENTHESIS)) {
            JSONObject jsonObject = JSONObject.fromObject(response.getBody());

            return Response.failure((Integer) jsonObject.get(PooulContracts.CODE), (String) jsonObject.get(PooulContracts.MSG));
        }

        //获取返回token
        Map<String, Claim> claims = PooulToken.getClaims(response.getBody());
        if (claims.get(PooulContracts.CODE).asInt().equals(NumberUtils.INTEGER_ZERO)) {
//            PooulOrderQueryDTO queryDTO = claims.get(PooulContracts.DATA).as(PooulOrderQueryDTO.class);
            return Response.success(claims.get(PooulContracts.CODE).asInt(), claims.get(PooulContracts.MSG).asString(), claims.get(PooulContracts.DATA).asMap());
        } else {
            return Response.failure(claims.get(PooulContracts.CODE).asInt(), claims.get(PooulContracts.MSG).asString());
        }
    }

    @Override
    public Response close(String mchTradeId) {
        PooulOrderDO pooulOrderDO = pooulOrderService.one(PooulOrderQO.builder().mchTradeId(mchTradeId).build());
//        ApiPreconditions.is(null == pooulOrderDO,ApiCode.BASED_ON,"订单不存在");

        Map<String,Object> map = Maps.newHashMap();
        map.put("mch_trade_id",mchTradeId);
        map.put("nonce_str",NumberGenerator.uuid32());
        String token = PooulToken.create(map);

        HttpResponse<String> response = null;
        try {
            response = Unirest.post(pooulProperties.getCloseOrderUrl()).body(token).asString();
        } catch (UnirestException e) {
            log.error("Pooul关闭订单失败:", e);
            ApiAssert.state(false, PooulCode.Message.ORDER_CLOSE_FAILURE, e.getMessage());
        }

        //如果已"{"开始表明创建订单失败，返回错误信息
        if (response.getBody().startsWith(PooulContracts.RIGHT_PARENTHESIS)) {
            JSONObject jsonObject = JSONObject.fromObject(response.getBody());
            return Response.failure((Integer) jsonObject.get(PooulContracts.CODE), (String) jsonObject.get(PooulContracts.MSG));
        }

        //获取返回token
        Map<String, Claim> claims = PooulToken.getClaims(response.getBody());
        if (claims.get(PooulContracts.CODE).asInt().equals(NumberUtils.INTEGER_ZERO)) {
            pooulOrderDO.setPayStatus(TradeStateEnum.CLOSED);
            pooulOrderService.update(pooulOrderDO);
            return Response.success(claims.get(PooulContracts.CODE).asInt(), claims.get(PooulContracts.MSG).asString());
        } else {
            return Response.failure(claims.get(PooulContracts.CODE).asInt(), claims.get(PooulContracts.MSG).asString());
        }
    }

    @Override
    public Response reverse(String mchTradeId) {
        return null;
    }

}
