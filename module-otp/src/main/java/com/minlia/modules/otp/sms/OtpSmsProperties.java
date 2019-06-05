package com.minlia.modules.otp.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chen junhan 569551869@qq.com
 */
@ConfigurationProperties(prefix = "system.sms.otp")
@Getter
@Setter
public class OtpSmsProperties {

    private String defaultIcc;

    /**
     * 协议及域名
     */
    private String url;

    /**
     * jecto 客户端id
     */
    private String xJetcoClientId;

    /**
     * jecto 客户端密钥
     */
    private String xJetcoClientSecret;

}
