package com.minlia.modules.aliyun.oss.api.config;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by will on 4/1/17.
 */

@Component
@ConfigAutowired(type = "SYS_OSS_ALIYUN_CONFIG")
@Data
public class AliyunOssConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String bucket;

    private String endpoint;

    /**
     * 域名
     */
    private String domain;

}