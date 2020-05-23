package com.minlia.modules.rebecca.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "minlia.rebecca")
@Data
public class RebeccaProperties {

    private Boolean enableSecuredAnnotationScan;

}
