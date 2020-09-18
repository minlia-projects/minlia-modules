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
 * @author garen
 * @date 2018/8/27
 */
public class AliyunOssClient {

    private static OSS ossClient;
    private static AliyunOssConfig aliyunOssConfig;

    public AliyunOssClient() {
    }

    public static OSS instance() {
        if (null == ossClient) {
            AliyunOssConfig config = getConfig();
            ossClient = (new OSSClientBuilder()).build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        }
        return ossClient;
    }

    public static AliyunOssConfig getConfig() {
        if (null == aliyunOssConfig) {
            AliyunOssConfig config = (AliyunOssConfig) ContextHolder.getContext().getBean(AliyunOssConfig.class);
            ApiAssert.notNull(config.getAccessKeyId(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG, new Object[0]);
            ApiAssert.notNull(config.getAccessKeySecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG, new Object[0]);
            ApiAssert.notNull(config.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG, new Object[0]);
            ApiAssert.notNull(config.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG, new Object[0]);
            aliyunOssConfig = config;
        }
        return aliyunOssConfig;
    }

    public static URL generatePresignedUrl(String objectName, long seconds) {
        Date expiration = new Date((new Date()).getTime() + seconds * 1000L);
        URL url = instance().generatePresignedUrl(getConfig().getBucket(), objectName.replace(aliyunOssConfig.getDomain(), ""), expiration);
        return url;
    }

    public static URL generatePresignedUrl(String objectName, long seconds, String style) {
        if (StringUtils.isBlank(style)) {
            return generatePresignedUrl(objectName, seconds);
        } else {
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(getConfig().getBucket(), objectName.replace(aliyunOssConfig.getDomain(), ""), HttpMethod.GET);
            req.setExpiration(new Date((new Date()).getTime() + seconds * 1000L));
            req.setProcess(style);
            URL signedUrl = instance().generatePresignedUrl(req);
            return signedUrl;
        }
    }

}