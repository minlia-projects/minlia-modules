package com.minlia.module.pooul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/7/18.
 */
@Component
@ConfigurationProperties(prefix = "pooul",ignoreUnknownFields = true)
@Data
public class PooulProperties {

    private String host;

    private String username;

    private String password;

}
