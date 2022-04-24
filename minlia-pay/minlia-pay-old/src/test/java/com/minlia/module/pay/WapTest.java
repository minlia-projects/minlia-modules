package com.minlia.module.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.junit.Test;

public class WapTest {

    @Test
    public void wap() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2021003107650460",
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCKuEYQulxnU4Y+3zAvCcgSBHM7YRb/ew+QAK/ouPTced7Y+60EoJOU3UGHpG06chtX7z14Y9enJdqNRwep9Tbd5JtYPb6NHNXoVgoboJQ3BQ4FrhtzGZTeS2j40aANrFpDnfyfozOa8k/OSMPgwZrSucXiae1sek27apZ6Ya1PjpzjnO2OJyZvqIqFLbKhgdjni6mV4opo06DEQhiOUlF4NNSDCUtCmxVV/kLqnDJ95pCN4+qHBfWfGkKkufcj86P9vSIyRMdWdRwi9WVEFYrBslfcz7JLxoryU/DueZ/IZU9HTOycO93xyo7LmZPFFkBd3MPgVLLE/3r2OqJVgwsvAgMBAAECggEAEjfXQ9Ul05Adnl4zdsoDE5KwC4lZut3Pkmr0r0yzI82Bnd+XvnbUSwWv2H+YtMdNngPWf9wk81e+VzWP5RtGRGgEmtcpEmFKGoRKOYyiSoOIbuiJxFkEuxkORTdKnOEYza6PAewuF9hg8MePqhHjIm+xdGXS/58kXEvITXRMjZkMeuiAhiGThcGLZdJrVFDHLUZHtotSDWAgquW0yZsL14d7k8mzZy+D/xElgZ7TpTb3Kzg3i/uTMM4HWav4OIVl+jlpNQ0mXYwcqio+TsyMf8wXkfVB4tpeWJqiphOT4Oawi7h9skwpXs+BxrNH49WVfCeNLsINB9Vu4xKF29IOuQKBgQDPOtg1IWCKrWmf+ppLVlRBt78SVhJdimt5iUjqX9gQM5XCSB+Qd/oUGS4gHdjiOYQKRzmP/i1RVJCX29b0lKyo7pEm2KSnPm8haP1smR3jH7x04s48ygmw5xN7GgBCswQITjNu6AGfHOmqgp2+bvUGCDYnHFFzAfyqul33xUW6QwKBgQCrXdiEPIeA66V3XVd/AcTCwkz+IubaA19RdwsnmVmVZQCJX5ueRWlR8X+5EdEbQ3HUtBROVXnf6kCc9oh5jDmcdQ+BNexJDyzoMCvFHx51+xz1U6CPaOydCpkWbBks4p8CyhMN2JP1/b/UXcXFt8NCbrkpgF6gZqT7WLNifEEqpQKBgGHlep4e1mys32kkCh/s+IDTPQB8iNCHw532rOuLu5ZryCOvzrmA9DLqck/ZycuVQDtaymarvNzfBi+3OyTv12E20nt3fNFFPVqbLp1O+D+DjpPpUWSGTW20vA87iUP5Lb5+su2BzMJN9lUGv15nLHldB7gSOKgMGEDPKfUSzI3hAoGACz21tT7vOJK8oO7bjXZFPO1X9Ze+gvCTsRlY/vj9OmKMvXYrFB/Df9+SnEZB7ELpVaWWa2kJ/Jk2MdWuMCL1qIC5SqWJ4WUAch6u0FhxPBYD2UfqEV41nzuRvTnMEPiwq5Mr2s2pLwq9KaYZEhYy1cnDlahZDLaO/fnckopKEA0CgYB7IbZvn0JBXa9WKeqrmzEBpro9QblDI8SZVDOy2OcCNMscpaOLSs/RMXjRTRLh3up1eyw0GAswIiai+48TMeCKA5pDdY3UVeSbQphDTo/SrQdqIkViIp9/ZYHSRVoihlC6YFJizoX429AYMaiexaMTSEqRKdH+toD+Pfph5pO7ug==",
                "json",
                "UTF-8",
                "alipay_public_key",
                "RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl("");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "20210817010101004");
        bizContent.put("total_amount", 0.01);
        bizContent.put("subject", "测试商品");
        bizContent.put("product_code", "QUICK_WAP_WAY");
        //bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
