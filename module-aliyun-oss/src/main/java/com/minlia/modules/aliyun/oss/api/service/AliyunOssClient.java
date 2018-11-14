package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.OSSClient;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;
import com.minlia.modules.aliyun.oss.api.constant.AliyunOssCode;

/**
 * Created by garen on 2018/8/27.
 */
public class AliyunOssClient {

    private static OSSClient ossClient;

    public static OSSClient ossClient(){
        if (null == ossClient) {
            AliyunOssProperties properties = ContextHolder.getContext().getBean(AliyunOssProperties.class);
            ApiAssert.notNull(properties.getKey(), AliyunOssCode.Message.ACCESSKEYID_NOT_CONFIG);
            ApiAssert.notNull(properties.getSecret(), AliyunOssCode.Message.SECRETACCESSKEY_NOT_CONFIG);
            ApiAssert.notNull(properties.getBucket(), AliyunOssCode.Message.BUCKET_NOT_CONFIG);
            ApiAssert.notNull(properties.getEndpoint(), AliyunOssCode.Message.ENDPOINT_NOT_CONFIG);
            ossClient = new OSSClient(properties.getEndpoint(), properties.getKey(), properties.getSecret());
        }
        return ossClient;
    }

}
