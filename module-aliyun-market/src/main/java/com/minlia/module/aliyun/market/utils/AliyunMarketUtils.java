package com.minlia.module.aliyun.market.utils;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.aliyun.market.bean.dto.BankCardVerifyDTO;
import com.minlia.module.aliyun.market.bean.to.BankCardVerifyTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by garen on 2018/8/16.
 */
@Slf4j
public class AliyunMarketUtils {

    private final static String url = "http://lundroid.market.alicloudapi.com/lianzhuo/verifi";

    public static BankCardVerifyDTO verifyBankCard(String appcode, BankCardVerifyTO to) {
        ApiAssert.hasLength(appcode, "银行卡验证appcode不能为空");
        Map<String, Object> querys = new HashMap<String, Object>();
        querys.put("acct_pan", to.getNumber());
        if (StringUtils.isNotBlank(to.getHolder())) {
            querys.put("acct_name", to.getHolder());
        }
        if (StringUtils.isNotBlank(to.getIdNumber())) {
            querys.put("cert_id", to.getIdNumber());
        }
        if (StringUtils.isNotBlank(to.getCellphone())) {
            querys.put("phone_num", to.getCellphone());
        }

        BankCardVerifyDTO dto = null;
        try {
            HttpResponse<String> response = Unirest.get(url).header("Authorization", "APPCODE " + appcode).queryString(querys).asString();
            if (response.getStatus() == HttpStatus.OK.value()) {
                dto = new Gson().fromJson(String.valueOf(response.getBody()),BankCardVerifyDTO.class);
            } else {
                ApiAssert.state(false, response.getStatus(), response.getStatusText());
            }
        } catch (Exception e) {
            log.error("银行卡验证异常：",e);
            ApiAssert.state(false, "银行卡验证异常" + e.getMessage());
        }
        return dto;
    }

    public static void main(String[] args) {
        BankCardVerifyTO to = new BankCardVerifyTO("62260978062215121","候志朋",null,null);
        BankCardVerifyDTO dto = verifyBankCard("6889a6bedf53468ea27d10f12a8e5159",to);
    }

}
