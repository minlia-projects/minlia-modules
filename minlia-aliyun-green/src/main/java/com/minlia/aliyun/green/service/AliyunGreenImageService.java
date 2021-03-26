package com.minlia.aliyun.green.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.minlia.aliyun.green.bean.AliyunGreenImageResult;
import com.minlia.aliyun.green.config.AliyunGreenConfig;
import com.minlia.aliyun.green.constant.AliyunGreenCode;
import com.minlia.aliyun.green.enums.GreenImageLabelEnum;
import com.minlia.cloud.utils.ApiAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliyunGreenImageService {

    private final AliyunGreenConfig aliyunGreenConfig;
    private static IAcsClient client = null;

    public AliyunGreenImageResult handle(String url) {
        return handle(url, false);
    }

    public AliyunGreenImageResult handle(String url, boolean throwException) {
        ImageSyncScanRequest imageSyncScanRequest = getRequest();
        JSONObject httpBody = getData(GreenImageLabelEnum.all(), url);
        imageSyncScanRequest.setHttpContent(StringUtils.getBytesUtf8(httpBody.toJSONString()), "UTF-8", FormatType.JSON);

        AliyunGreenImageResult result = null;
        try {
            HttpResponse httpResponse = client.doAction(imageSyncScanRequest);
            log.info("result {}", new String(httpResponse.getHttpContent(), "UTF-8"));
            log.info("result {}", new String(httpResponse.getHttpContent(), "UTF-8"));
            log.info("result {}", new String(httpResponse.getHttpContent(), "UTF-8"));
            log.info("result {}", new String(httpResponse.getHttpContent(), "UTF-8"));
            log.info("result {}", new String(httpResponse.getHttpContent(), "UTF-8"));
            result = AliyunGreenImageResult.format(new String(httpResponse.getHttpContent(), "UTF-8"));
        } catch (Exception e) {
            ApiAssert.state(true, e.getMessage());
        }
        if (throwException) {
            ApiAssert.state(result.isSuccess() && result.isPass(), AliyunGreenCode.Message.valueOf(result.getLabel()));
        }
        return result;
    }

    @PostConstruct
    public void init() {
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", aliyunGreenConfig.getAccessKeyId(), aliyunGreenConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        client = new DefaultAcsClient(profile);
    }

    public static ImageSyncScanRequest getRequest() {
        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定API返回格式。
        imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
        // 指定请求方法。
        imageSyncScanRequest.setMethod(MethodType.POST);
        imageSyncScanRequest.setEncoding("utf-8");
        // 支持HTTP和HTTPS。
        imageSyncScanRequest.setProtocol(ProtocolType.HTTP);
        /**
         * 请设置超时时间。服务端全链路处理超时时间为10秒，请做相应设置。
         * 如果您设置的ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常。
         */
        imageSyncScanRequest.setConnectTimeout(3000);
        imageSyncScanRequest.setReadTimeout(10000);
        return imageSyncScanRequest;
    }

    private static JSONObject getData(List scenes, String url) {
        JSONObject httpBody = new JSONObject();
        /**
         * 设置要检测的风险场景。计费依据此处传递的场景计算。
         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算。
         * 例如，检测2张图片，场景传递porn和terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算。
         * porn：表示鉴黄场景。
         */
        httpBody.put("scenes", scenes);

        /**
         * 设置待检测图片。一张图片对应一个task。
         * 多张图片同时检测时，处理的时间由最后一个处理完的图片决定。
         * 通常情况下批量检测的平均响应时间比单张检测的要长。一次批量提交的图片数越多，响应时间被拉长的概率越高。
         * 这里以单张图片检测作为示例, 如果是批量图片检测，请自行构建多个task。
         */
        JSONObject task = new JSONObject();
        task.put("dataId", UUID.randomUUID().toString());

        // 设置图片链接。
        task.put("url", url);
        task.put("time", new Date());
        httpBody.put("tasks", Arrays.asList(task));
        return httpBody;
    }

//    public static void main(String[] args) {
//        antispam("https://img14.360buyimg.com/n7/jfs/t1/8334/3/11170/355609/5c2eb591E025c3d23/79d09e8ee9e8e408.jpg", true);
//    }

}