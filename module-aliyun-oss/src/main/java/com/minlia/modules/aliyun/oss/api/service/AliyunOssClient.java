package com.minlia.modules.aliyun.oss.api.service;

import com.aliyun.oss.OSSClient;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.modules.aliyun.oss.api.config.AliyunOssProperties;

/**
 * Created by garen on 2018/8/27.
 */
public class AliyunOssClient {

    private static OSSClient ossClient;

    public static OSSClient ossClient(){
        if (null == ossClient) {
            AliyunOssProperties properties = ContextHolder.getContext().getBean(AliyunOssProperties.class);
            ossClient = new OSSClient(properties.getEndpoint(), properties.getKey(), properties.getSecret());
        }
        return ossClient;
    }

}
