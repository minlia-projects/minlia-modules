package com.minlia.module.aliyun.market.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by garen on 4/1/17.
 */

@ConfigurationProperties(prefix = "aliyun.market", ignoreUnknownFields = false)
@Data
public class AliyunMarketProperties {

    /**
     * https://market.aliyun.com/products/57000002/cmapi014429.html?spm=5176.2020520132.101.54.2kjQYD#sku=yuncode842900008
     */
    private String bankcardVerifyLianzhuo;

}
