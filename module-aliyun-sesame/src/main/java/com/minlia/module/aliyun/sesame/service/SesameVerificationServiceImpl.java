package com.minlia.module.aliyun.sesame.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minlia.module.aliyun.sesame.body.SesameVerificationResponse;
import com.minlia.module.aliyun.sesame.utils.HttpUtils;
import com.minlia.module.aliyun.sesame.body.SesameVerificationRequest;
import com.minlia.module.aliyun.sesame.enumeration.SesameVerifyCodeEnum;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 3/13/17.
 */
@Service
public class SesameVerificationServiceImpl implements SesameVerificationService {

    @Override
    public SesameVerificationResponse verification(SesameVerificationRequest request) {
        String host = "https://dm-101.data.aliyun.com";
        String path = "/rest/161225/zmxy/api/zhima.credit.antifraud.verify.json";
        String method = "POST";
        String appcode = "6889a6bedf53468ea27d10f12a8e5159";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("name", request.getName());
        bodys.put("certNo", request.getCertNo());
        bodys.put("certType", "IDENTITY_CARD");

//        bodys.put("address", "杭州市西湖区天目山路266号");
//        bodys.put("bankCard", "20110602436748024138");
//        bodys.put("email", "jnlxhy@alitest.com");
//        bodys.put("imei", "868331011992179");
//        bodys.put("ip", "101.247.161.1");
//        bodys.put("mac", "44-45-53-54-00-00");
//        bodys.put("mobile", "15843991158");
//        bodys.put("wifimac", "00-00-00-00-00-E0");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            Gson gson = new GsonBuilder().create();
            SesameVerificationResponse responseBody = gson.fromJson(EntityUtils.toString(response.getEntity()), SesameVerificationResponse.class);

            if (responseBody.getSuccess()) {
                SesameVerifyCodeEnum sesameVerifyCode = responseBody.getData().getVerifyCode().get(0);
                responseBody.setCode(sesameVerifyCode.name());
                responseBody.setMessage(sesameVerifyCode.getDesc());
                if (!sesameVerifyCode.equals(SesameVerifyCodeEnum.V_CN_NM_MA)) {
                    responseBody.setSuccess(false);
                }
            }
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return SesameVerificationResponse.builder().success(false).code("-1").message(e.getMessage()).build();
        }
    }
}
