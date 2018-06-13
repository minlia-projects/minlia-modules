package com.minlia.module.lbsyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lbs", ignoreUnknownFields = false)
public class LbsProperties {

    private String ak;

    private String geotableId;

}
