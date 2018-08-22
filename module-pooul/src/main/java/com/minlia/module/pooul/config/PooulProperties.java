package com.minlia.module.pooul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by garen on 2018/7/18.
 */
@ConfigurationProperties(prefix = "pooul",ignoreUnknownFields = true)
@Data
public class PooulProperties {

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户公钥
     */
    private String publicKey;

    /**
     * 商户私钥
     */
    private String privateKey;

    /**
     * 普尔公钥
     */
    private String pooulPublicKey;

    /**
     * 统计支付URL
     */
    private String urlV2Pay;

    /**
     * 通知地址
     */
    private String notifyUrl;

}
