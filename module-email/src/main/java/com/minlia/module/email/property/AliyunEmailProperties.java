package com.minlia.module.email.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aliyun.email")
@Data
public class AliyunEmailProperties {

    private String regionId;

    private String accessKey;

    private String accessSecret;

    private String accountName;

    private String fromAlias;

    private String tagName;

}
