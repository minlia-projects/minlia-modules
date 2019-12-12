package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssConfig;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;

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
            AliyunOssConfig config = ContextHolder.getContext().getBean(AliyunOssConfig.class);
            ApiAssert.notNull(config.getAccessKeyId(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG);
            ApiAssert.notNull(config.getAccessKeySecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG);
            ApiAssert.notNull(config.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG);
            ApiAssert.notNull(config.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG);
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

    public static URL generatePresignedUrl(String objectName, long seconds) {
        Date expiration = new Date(new Date().getTime() + seconds * 1000);
        URL url = AliyunOssClient.instance().generatePresignedUrl(aliyunOssConfig.getBucket(), objectName.replace(aliyunOssConfig.getDomain(), ""), expiration);
        return url;
    }

}