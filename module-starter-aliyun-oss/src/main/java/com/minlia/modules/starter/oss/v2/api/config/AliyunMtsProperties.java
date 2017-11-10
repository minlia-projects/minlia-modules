package com.minlia.modules.starter.oss.v2.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyun.mts", ignoreUnknownFields = false)
@Data
public class AliyunMtsProperties {
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String pipelineId;
    private String ossLocation;
    private String inputBucket;
    private String outputBucket;
}
