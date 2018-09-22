package com.minlia.module.pooul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/7/18.
 */
@Component
@ConfigurationProperties(prefix = "pooul.bankcard",ignoreUnknownFields = true)
@Data
public class PooulBankCardProperties {

    private String createUrl;

    private String updateUrl;

    private String deleteUrl;

    private String getUrl;

    private String searchUrl;

}
