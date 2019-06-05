package com.minlia.module.sms.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.sms", ignoreInvalidFields = true, ignoreNestedProperties = true)
@Data
public class SmsProperties {

    private String type;

}
