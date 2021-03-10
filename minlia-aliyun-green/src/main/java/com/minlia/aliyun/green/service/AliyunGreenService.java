package com.minlia.aliyun.green.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.minlia.aliyun.green.bean.AliyunGreenResult;
import com.minlia.aliyun.green.config.AliyunGreenConfig;
import com.minlia.aliyun.green.constant.AliyunGreenCode;
import com.minlia.aliyun.green.enums.GreenLabelEnum;
import com.minlia.cloud.utils.ApiAssert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AliyunGreenService {

    private final AliyunGreenConfig aliyunGreenConfig;
    private static IAcsClient client = null;
    private static TextScanRequest textScanRequest = request();

    public AliyunGreenResult antispam(String content) {
        return antispam(content, false);
    }

    public AliyunGreenResult antispam(String content, boolean throwException) {
        AliyunGreenResult aliyunGreenResult = null;
        try {
            textScanRequest.setHttpContent(getData(Arrays.asList("antispam"), content), "UTF-8", FormatType.JSON);
            textScanRequest.setConnectTimeout(3000);
            textScanRequest.setReadTimeout(6000);
            HttpResponse httpResponse = client.doAction(textScanRequest);
            aliyunGreenResult = AliyunGreenResult.format(new String(httpResponse.getHttpContent(), "UTF-8"));
        } catch (Exception e) {
            ApiAssert.state(true, e.getMessage());
        }

        if (throwException) {
            ApiAssert.state(aliyunGreenResult.isSuccess() && aliyunGreenResult.isPass(), AliyunGreenCode.Message.valueOf(aliyunGreenResult.getLabel()));
        }
        return aliyunGreenResult;
    }

    @PostMapping
    public void init() {
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", aliyunGreenConfig.getAccessKeyId(), aliyunGreenConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        client = new DefaultAcsClient(profile);
    }

    public static TextScanRequest request() {
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId("cn-shanghai");
        return textScanRequest;
    }

    private static byte[] getData(List scenes, String content) throws UnsupportedEncodingException {
        List<Map<String, Object>> tasks = new ArrayList();
        Map<String, Object> task1 = new LinkedHashMap();
        task1.put("dataId", UUID.randomUUID().toString());
        //待检测的文本，长度不超过10000个字符。
        task1.put("content", content);
        tasks.add(task1);
        JSONObject data = new JSONObject();
        //检测场景。文本垃圾检测请传递antispam。
        data.put("scenes", scenes);
        data.put("tasks", tasks);
        return data.toJSONString().getBytes("UTF-8");
    }

}