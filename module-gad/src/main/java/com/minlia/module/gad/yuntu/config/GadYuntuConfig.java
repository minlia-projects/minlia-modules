package com.minlia.module.gad.yuntu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gad", ignoreUnknownFields = false)
@Data
public class GadYuntuConfig {

    private String yuntuTableId;

    private String webApiKey;

}
