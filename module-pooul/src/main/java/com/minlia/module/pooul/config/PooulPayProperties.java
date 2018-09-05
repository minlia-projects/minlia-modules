package com.minlia.module.pooul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by garen on 2018/7/18.
 */
@ConfigurationProperties(prefix = "pooul.pay",ignoreUnknownFields = true)
@Data
public class PooulPayProperties {

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
     * 支付URL
     */
    private String payUrl;

    /**
     * 查询订单URL
     */
    private String queryOrderUrl;

    /**
     * 关闭订单
     */
    private String closeOrderUrl;

    /**
     * 通知地址
     */
    private String notifyUrl;

}
