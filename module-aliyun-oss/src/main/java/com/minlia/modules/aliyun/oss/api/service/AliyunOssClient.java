package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;

import java.net.URL;
import java.util.Date;

/**
 * Created by garen on 2018/8/27.
 */
public class AliyunOssClient {

    private static OSS ossClient;

    private static AliyunOssProperties aliyunOssProperties;

    public static OSS instance() {
        if (null == ossClient) {
            AliyunOssProperties properties = ContextHolder.getContext().getBean(AliyunOssProperties.class);
            ApiAssert.notNull(properties.getKey(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG);
            ApiAssert.notNull(properties.getSecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG);
            ApiAssert.notNull(properties.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG);
            ApiAssert.notNull(properties.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG);
            ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getKey(), properties.getSecret());
            aliyunOssProperties = properties;
        }
        return ossClient;
    }

    public static URL generatePresignedUrl(String objectName, long seconds) {
        Date expiration = new Date(new Date().getTime() + seconds * 1000);
        URL url = AliyunOssClient.instance().generatePresignedUrl(aliyunOssProperties.getBucket(), objectName, expiration);
        return url;
    }

}