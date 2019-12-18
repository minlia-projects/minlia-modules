package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssConfig;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Date;

/**
 * Created by garen on 2018/8/27.
 */
public class AliyunOssClient {

    private static OSS ossClient;

//    private static AliyunOssProperties aliyunOssProperties;

    private static AliyunOssConfig aliyunOssConfig;

    public static OSS instance() {
        if (null == ossClient) {
            AliyunOssConfig config = getConfig();
            ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            aliyunOssConfig = config;

//            AliyunOssProperties properties = ContextHolder.getContext().getBean(AliyunOssProperties.class);
//            ApiAssert.notNull(properties.getKey(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG);
//            ApiAssert.notNull(properties.getSecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG);
//            ApiAssert.notNull(properties.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG);
//            ApiAssert.notNull(properties.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG);
//            ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getKey(), properties.getSecret());
//            aliyunOssProperties = properties;
        }
        return ossClient;
    }

    public static AliyunOssConfig getConfig() {
        if (null == aliyunOssConfig) {
            AliyunOssConfig config = ContextHolder.getContext().getBean(AliyunOssConfig.class);
            ApiAssert.notNull(config.getAccessKeyId(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG);
            ApiAssert.notNull(config.getAccessKeySecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG);
            ApiAssert.notNull(config.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG);
            ApiAssert.notNull(config.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG);
            ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            aliyunOssConfig = config;
        }
        return aliyunOssConfig;
    }

    public static URL generatePresignedUrl(String objectName, long seconds) {
        Date expiration = new Date(new Date().getTime() + seconds * 1000);
        URL url = AliyunOssClient.instance().generatePresignedUrl(getConfig().getBucket(), objectName.replace(aliyunOssConfig.getDomain(), ""), expiration);
        return url;
    }

    /**
     * @param objectName
     * @param seconds    指定过期时间
     * @param style      图片处理样式
     * @return
     */
    public static URL generatePresignedUrl(String objectName, long seconds, String style) {
        if (StringUtils.isBlank(style)) {
            return generatePresignedUrl(objectName, seconds);
        }
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(getConfig().getBucket(), objectName.replace(aliyunOssConfig.getDomain(), ""), HttpMethod.GET);
        req.setExpiration(new Date(new Date().getTime() + seconds * 1000));
        req.setProcess(style);
        URL signedUrl = AliyunOssClient.instance().generatePresignedUrl(req);
        // 关闭OSSClient。
//        ossClient.shutdown();
        return signedUrl;
    }

}