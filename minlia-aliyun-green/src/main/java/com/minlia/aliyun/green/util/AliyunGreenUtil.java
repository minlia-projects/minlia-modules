package com.minlia.aliyun.green.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.minlia.aliyun.green.bean.AliyunGreenResult;
import com.minlia.cloud.utils.ApiAssert;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class AliyunGreenUtil {

    private static IAcsClient client = client();
    private static TextScanRequest textScanRequest = request();

    public static IAcsClient client() {
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", "", "");
        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
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

    public static AliyunGreenResult antispam(String content) {
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
        return aliyunGreenResult;
    }

    public static void main(String[] args) {
        AliyunGreenResult result = antispam("本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932");
        System.out.println(result.isSuccess());
        System.out.println(result.isBlock());
        System.out.println(result.getLabel());
        AliyunGreenResult result1 = antispam("本校小额贷款，安全、快捷、方便、无抵押，随机随贷，当天放款，上门服务。联系weixin 946932");
    }

}