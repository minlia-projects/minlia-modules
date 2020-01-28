package com.minlia.module.unified.payment.alipay.v1;

import com.minlia.module.unified.payment.config.Certificate;
import com.minlia.module.unified.payment.config.Config;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by will on 9/14/17.
 */
@ConfigurationProperties(prefix = "unifiedpayment.alipay", ignoreUnknownFields = false)
@Data
public class AlipayConfig implements Config {

    private Certificate certificate;

    private String appId;

    private String callback;

    private String notifyUrl;

    private String returnUrl;

    public AlipayConfig() {
        if (null != this.getCertificate()) {
            if (StringUtils.isEmpty(this.getCertificate().getPlatformPublicKey())) {
                throw new RuntimeException("支付宝支付需配置平台公钥");
            }
//            if (StringUtils.isEmpty(this.getCertificate().getAppPublicKey())) {
//                throw new RuntimeException("支付宝支付需配置应用公钥");
//            }
            if (StringUtils.isEmpty(this.getCertificate().getAppPrivateKey())) {
                throw new RuntimeException("支付宝支付需配置应用私钥钥");
            }
        }
    }

}
