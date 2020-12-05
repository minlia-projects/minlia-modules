package com.minlia.module.email.property;

import com.minlia.module.common.annotation.ConfigAutowired;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.email")
@Data
public class EmailProperties {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol = "smtp";

}