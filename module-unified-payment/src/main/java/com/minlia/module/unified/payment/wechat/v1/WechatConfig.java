package com.minlia.module.unified.payment.wechat.v1;

import com.minlia.module.unified.payment.config.Certificate;
import com.minlia.module.unified.payment.config.Config;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by will on 9/14/17.
 * 精减配置, 只配置需要的部分
 */
@ConfigurationProperties(prefix = "unifiedpayment.wechat", ignoreUnknownFields = false)
@Data
public class WechatConfig implements Config {

    private Certificate certificate;

    private String appId;

    private String mchId;

    private String key;

    private String callback;

    public WechatConfig() {
        if (null != this.getCertificate()) {
            if (!StringUtils.isEmpty(this.getCertificate().getPlatformPublicKey())) {
                throw new RuntimeException("微信支付无需配置平台公钥");
            }
            if (!StringUtils.isEmpty(this.getCertificate().getAppPublicKey())) {
                throw new RuntimeException("微信支付无需配置应用公钥");
            }
            if (!StringUtils.isEmpty(this.getCertificate().getAppPrivateKey())) {
                throw new RuntimeException("微信支付无需配置应用私钥钥");
            }
            //但是可以配置证书路径
        }
    }

}
