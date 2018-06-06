package com.minlia.modules.qcloud.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by will on 4/1/17.
 */
@ConfigurationProperties(prefix = "qcloud.cos", ignoreUnknownFields = false)
@Data
public class QcloudCosConfig {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    /**
     * 域名
     */
    private String domain;

    private String imageDomain;


}
