package com.minlia.module.pay.bean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

@NoArgsConstructor
@Data
public class SysPayTransferResult {

    @JsonProperty("alipay_fund_trans_uni_transfer_response")
    private AlipayFundTransUniTransferResponseDTO alipayFundTransUniTransferResponse;

    @JsonProperty("sign_a")
    private String sign;

    @NoArgsConstructor
    @Data
    public static class AlipayFundTransUniTransferResponseDTO {
        @JsonProperty("code")
        private String code;
        @JsonProperty("msg")
        private String msg;
        @JsonProperty("sub_code")
        private String subCode;
        @JsonProperty("sub_msg")
        private String subMsg;

        @JsonProperty("out_biz_no")
        private String outBizNo;
        @JsonProperty("order_id")
        private String orderId;
        @JsonProperty("pay_fund_order_id")
        private String payFundOrderId;
        @JsonProperty("status")
        private String status;
        @JsonProperty("trans_date")
        private String transDate;
    }

    public boolean isSuccess() {
        return this.alipayFundTransUniTransferResponse.code.equals("10000") && this.alipayFundTransUniTransferResponse.getStatus().equals("SUCCESS");
    }

    public static SysPayTransferResult build(Map map) {
        return JSON.parseObject(JSON.toJSONString(map), SysPayTransferResult.class);
    }

    public static void main(String[] args) {
        Map<String ,Object> map = Maps.newHashMap();
        Map<String ,Object> alipay_fund_trans_uni_transfer_response = Maps.newHashMap();
        alipay_fund_trans_uni_transfer_response.put("code", "10000");
        alipay_fund_trans_uni_transfer_response.put("status", "SUCCESS");
        map.put("alipay_fund_trans_uni_transfer_response", alipay_fund_trans_uni_transfer_response);
        SysPayTransferResult result = JSON.parseObject(JSON.toJSONString(map), SysPayTransferResult.class);
        System.out.println(result);
        System.out.println(result.isSuccess());
    }

}