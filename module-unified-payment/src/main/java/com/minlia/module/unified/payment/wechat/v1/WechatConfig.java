package com.minlia.module.unified.payment.wechat.v1;

import com.minlia.module.unified.payment.config.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by will on 9/14/17.
 * 精减配置, 只配置需要的部分
 */
@ConfigurationProperties(prefix = "unifiedpayment.wechat", ignoreUnknownFields = false)
@Data
public class WechatConfig implements Config {

    private String appId;

    private String mchId;

    private String key;

    private String callback;

}
